package com.jason.daisy

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.time.temporal.ChronoUnit

fun millisToTimeString(millis : Long) : String {
    var s = ""
    var remainder = millis / 10
    val hourDiv = 360000L
    val minuteDiv = 6000L
    val secondDiv = 100L

    val hourRes = remainder/hourDiv

    s += when {
        hourRes > 0 -> "${hourRes}:"
        else -> ""
    }

    remainder %= hourDiv
    val minRes = remainder/minuteDiv

    s += when {
        minRes > 0 -> {
            if(s.isNotEmpty()) {
                "${minRes.toString().padStart(2, '0')}:"
            } else {
                "$minRes:"
            }
        }
        s.isNotEmpty() -> "00:"
        else -> ""
    }

    remainder %= minuteDiv
    val secRes = remainder/secondDiv

    s += when {
        secRes > 0 -> {
            if (s.isNotEmpty()) {
                secRes.toString().padStart(2, '0')
            } else {
                secRes.toString()
            }
        }
        s.isNotEmpty() -> "00"
        else -> "0"
    }
    remainder %= secondDiv

    s += "."

    s += remainder.toString().padStart(2, '0')

    return s
}



class MainViewModel : ViewModel() {

    private var timerActive = false
    private var timeStart : LocalTime = LocalTime.now()
    var currentTime : MutableLiveData<String> = MutableLiveData()
    var timerColor : MutableLiveData<Int> = MutableLiveData()

    private fun startTimer() {
        timeStart = LocalTime.now()
        timerActive = true
        timerColor.postValue(Color.MAGENTA)
        Thread(Runnable {
            while (timerActive) {
                Thread.sleep(1)
                currentTime.postValue(millisToTimeString(timeStart.until(LocalTime.now(), ChronoUnit.MILLIS)))
            }
            timerColor.postValue(Color.BLACK)
        }).start()
    }

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

    private fun stopIfStarted() : Boolean {
        if(timerActive) {
            timerActive = false
            return true
        }
        return false
    }
}