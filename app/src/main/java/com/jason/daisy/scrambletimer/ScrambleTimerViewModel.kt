package com.jason.daisy.scrambletimer

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jason.daisy.common.msToTimeString
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.worldcubeassociation.tnoodle.puzzle.CubePuzzle
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class ScrambleTimerViewModel(application: Application) : AndroidViewModel(application) {
    private var puzzle : CubePuzzle = GetPuzzleUseCase().execute(GetDefaultPuzzleTypeUseCase().execute())
    private var timerActive = false
    private var timeStart : LocalTime = LocalTime.now()
    private var timeEnd : LocalTime = LocalTime.now()
    private val db = DaisyDatabase.getInstance(application.applicationContext)
    private val scrambles = mutableListOf<String>()
    val currentTime : MutableLiveData<String> = MutableLiveData()
    val timerColor : MutableLiveData<Int> = MutableLiveData()
    val scramble : MutableLiveData<String> = MutableLiveData()

    fun handleActionUp() : Boolean {
        if(!timerActive) startTimer()
        return true
    }

    fun handleActionDown() : Boolean {
        return if (stopIfStarted()) {
            true
        } else {
            timerColor.postValue(Color.GREEN)
            false
        }
    }

    fun updateScramble() = viewModelScope.launch(Dispatchers.Default) {
        scrambles.remove(scramble.value)
        updateScrambles()
        if(scrambles.isEmpty()) scramble.postValue(puzzle.generateScramble())
        else scramble.postValue(scrambles[0])
    }

    private fun updateScrambles() = viewModelScope.launch(Dispatchers.Default) {
        while(scrambles.size < 10) {
            scrambles.add(puzzle.generateScramble())
        }
    }

    private fun startTimer() {
        timeStart = LocalTime.now()
        timerActive = true
        timerColor.postValue(Color.MAGENTA)
        timerJob()
    }

    private fun stopIfStarted() : Boolean {
        if(timerActive) {
            timeEnd = LocalTime.now()
            saveTime()
            timerActive = false
            updateScramble()
            return true
        }
        return false
    }

    private fun saveTime() = viewModelScope.launch(Dispatchers.IO) {
        db.solveDao.insertAll(Solve(timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString(), LocalDateTime.now().toString(), scramble.value ?: ""))
    }

    private fun timerJob() = viewModelScope.launch(Dispatchers.Default) {
        while (timerActive) {
            delay(10L)
            currentTime.postValue(timeStart.until(LocalTime.now(), ChronoUnit.MILLIS).msToTimeString())
        }
        currentTime.postValue(timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString())
        timerColor.postValue(Color.BLACK)
    }
}
