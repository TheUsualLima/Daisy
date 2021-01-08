package com.jason.daisy

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

fun msToTimeString(millis : Long) : String {
    val s : String
    val hours = millis / 1000 / 60 / 60
    val minutes = millis / 1000 / 60 % 60
    val seconds = millis / 1000 % 60
    val milliseconds = millis % 1000 / 10

    s = when {
        hours > 0 -> "$hours:" +
                "${minutes.toString().padStart(2, '0')}:" +
                "${seconds.toString().padStart(2, '0')}." +
                milliseconds.toString().padStart(2, '0')
        minutes > 0 -> "$minutes:" +
                "${seconds.toString().padStart(2, '0')}." +
                milliseconds.toString().padStart(2, '0')
        else -> "$seconds." +
                milliseconds.toString().padStart(2, '0')
    }

    return s
}

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

    private fun showTimes() = viewModelScope.launch {
        val solves = db.solveDao.getAll()
        for((i, s) in solves.withIndex()) {
            Log.d("$i", s.toString())
        }
    }

    private fun saveTime() = viewModelScope.launch {
        db.solveDao.insertAll(Solve(msToTimeString(timeStart.until(timeEnd, ChronoUnit.MILLIS)), LocalDateTime.now().toString()))
    }

    private fun timerJob() = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            while (timerActive) {
                delay(1L)
                currentTime.postValue(msToTimeString(timeStart.until(LocalTime.now(), ChronoUnit.MILLIS)))
            }
            currentTime.postValue(msToTimeString(timeStart.until(timeEnd, ChronoUnit.MILLIS)))
            saveTime()
            timerColor.postValue(Color.BLACK)
        }
    }
}
