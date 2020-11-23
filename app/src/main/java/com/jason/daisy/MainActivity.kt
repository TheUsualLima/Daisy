package com.jason.daisy

import android.os.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val theTextView : TextView = findViewById(R.id.textView)
        theTextView.text = "This is our text view"

        var timerActive = false //wrapped into a reference object to be modified when captured in a closure

        val timerUIHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg : Message) {
                when(msg.what) {
                    0 -> {
                        val time = if (msg.obj.toString().length < 4) {
                            "${msg.obj.toString().padStart(4, '0')}"
                        } else {
                            msg.obj.toString()
                        }
                        theTextView.text = "${time.substring(0, time.length - 3)}.${time.substring(time.length-3, time.length - 1)}"
                    }
                }
            }
        }

        val layout : ConstraintLayout = findViewById(R.id.mainLayout)
        layout.setOnClickListener {
            if (!timerActive) {
                val timeStart = SystemClock.uptimeMillis()
                timerActive = true
                Thread(Runnable {
                    while(timerActive) {
                        Thread.sleep(1)
                        val m = Message()
                        m.what = 0
                        m.obj = (SystemClock.uptimeMillis() - timeStart)

                       timerUIHandler.sendMessage(m)
                    }
                }).start()
            } else {
                timerActive = false
            }
        }
    }
}