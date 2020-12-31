package com.jason.daisy

import android.graphics.Color
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var timerActive = false
    private var timeStart = 1L
    var currentTime : MutableLiveData<Long> = MutableLiveData()
    var timerColor : MutableLiveData<Int> = MutableLiveData()

    private fun startTimer() {
        timeStart = SystemClock.uptimeMillis()
        timerActive = true
        timerColor.postValue(Color.MAGENTA)
        Thread(Runnable {
            while (timerActive) {
                Thread.sleep(1)
                currentTime.postValue(SystemClock.uptimeMillis() - timeStart)
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