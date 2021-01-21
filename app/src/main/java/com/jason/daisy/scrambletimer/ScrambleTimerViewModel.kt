package com.jason.daisy.scrambletimer

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jason.daisy.common.msToTimeString
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.worldcubeassociation.tnoodle.scrambles.Puzzle
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class ScrambleTimerViewModel(application: Application) : AndroidViewModel(application) {
    private var scrambleUpdater : Job
    private var timeStart : LocalTime = LocalTime.now()
    private var timeEnd : LocalTime = LocalTime.now()
    private val db = DaisyDatabase.getInstance(application.applicationContext)
    private val scrambles = mutableListOf<String>()

    private var _timerActive = MutableLiveData<Boolean>()
    val timerActive: LiveData<Boolean> = _timerActive

    private val _currentTime : MutableLiveData<String> = MutableLiveData()
    val currentTime : LiveData<String> = _currentTime

    private val _timerColor : MutableLiveData<Int> = MutableLiveData()
    val timerColor : LiveData<Int> = _timerColor

    private val _scramble : MutableLiveData<String> = MutableLiveData()
    val scramble : LiveData<String> = _scramble

    var puzzle : Puzzle = GetPuzzleUseCase().execute(GetDefaultPuzzleTypeUseCase().execute())
        private set

    init {
        _timerActive.value = false
        scrambleUpdater = updateScrambleList()
        updateScramble()
    }

    fun handleActionUp() : Boolean {
        if (_timerActive.value == false) {
            startTimer()
            return true
        }
        return false
    }

    fun handleActionDown() : Boolean {
        return if (stopIfStarted()) true
        else {
            _timerColor.postValue(Color.GREEN)
            false
        }
    }

    fun changePuzzle(puzzleChosen: PuzzleType) {
        val puzzle = GetPuzzleUseCase().execute(puzzleChosen)
        if(puzzle::class != this.puzzle::class) {
            this.puzzle = puzzle
            clearScrambleList()
            updateScramble()
        }
    }

    private fun updateScramble() = viewModelScope.launch(Dispatchers.Default) {
        scrambles.remove(_scramble.value)
        if(!scrambleUpdater.isActive) scrambleUpdater = updateScrambleList()
        if(scrambles.isEmpty()) _scramble.postValue(puzzle.generateScramble())
        else _scramble.postValue(scrambles[0])
    }

    private fun updateScrambleList() = viewModelScope.launch(Dispatchers.Default) {
        while(scrambles.size < 10) {
            scrambles.add(puzzle.generateScramble())
        }
    }

    private fun clearScrambleList() { scrambles.clear() }

    private fun startTimer() {
        timeStart = LocalTime.now()
        _timerActive.value = true
        _timerColor.postValue(Color.MAGENTA)
        timerJob()
    }

    private fun stopIfStarted() : Boolean {
        if(_timerActive.value == true) {
            timeEnd = LocalTime.now()
            saveTime()
            _timerActive.value = false
            updateScramble()
            return true
        }
        return false
    }

    private fun saveTime() = viewModelScope.launch(Dispatchers.IO) {
        db.solveDao.insertAll(
                Solve(
                        GetPuzzleStringUseCase().execute(puzzle),
                        timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString(),
                        LocalDateTime.now().toString(),
                        _scramble.value ?: ""
                )
        )
    }

    private fun timerJob() = viewModelScope.launch(Dispatchers.Default) {
        while (_timerActive.value == true) {
            delay(10L)
            _currentTime.postValue(timeStart.until(LocalTime.now(), ChronoUnit.MILLIS).msToTimeString())
        }
        _currentTime.postValue(timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString())
        _timerColor.postValue(Color.BLACK)
    }
}
