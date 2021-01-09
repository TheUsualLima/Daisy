package com.jason.daisy

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
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var timerActive = false
    private var timeStart : LocalTime = LocalTime.now()
    private var timeEnd : LocalTime = LocalTime.now()
    private val db = DaisyDatabase.getInstance(application.applicationContext)
    var currentTime : MutableLiveData<String> = MutableLiveData()
    var timerColor : MutableLiveData<Int> = MutableLiveData()

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

    fun clearDb() = viewModelScope.launch {
        db.solveDao.deleteAll()
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
            timerActive = false
            return true
        }
        return false
    }

    private fun saveTime() = viewModelScope.launch {
        db.solveDao.insertAll(Solve(timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString(), LocalDateTime.now().toString()))
    }

    private fun timerJob() = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            while (timerActive) {
                delay(10L)
                currentTime.postValue(timeStart.until(LocalTime.now(), ChronoUnit.MILLIS).msToTimeString())
            }
            currentTime.postValue(timeStart.until(timeEnd, ChronoUnit.MILLIS).msToTimeString())
            saveTime()
            timerColor.postValue(Color.BLACK)
        }
    }
}
