package com.jason.daisy

import android.os.*
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jason.daisy.customviews.CustomConstraintLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val theTextView : TextView = findViewById(R.id.textView)

        var timerActive = false //wrapped into a reference object to be modified when captured in a closure

        val timerUIHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when(msg.what) {
                    0 -> {
                        val time = if (msg.obj.toString().length < 4) {
                            msg.obj.toString().padStart(4, '0')
                        } else {
                            msg.obj.toString()
                        }
                        val len = time.length
                        val str = "${time.substring(0, len - 3)}.${time.substring(len - 3, len - 1)}"
                        theTextView.text = str
                    }
                }
            }
        }

        val button : Button = findViewById(R.id.button)
        val toast = Toast.makeText(button.context, "I Love you", Toast.LENGTH_SHORT)
        button.setOnClickListener {
            toast.show()
        }

        val layout : CustomConstraintLayout = findViewById(R.id.mainLayout)

        layout.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Touch", "ACTION DOWN")
                    if(timerActive) {
                        layout.performClick()
                        timerActive = false
                        timerActive
                    } else true
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("Touch", "ACTION UP")

                    if (!timerActive) {
                        layout.performClick()
                        val timeStart = SystemClock.uptimeMillis()
                        timerActive = true
                        Thread(Runnable {
                            while (timerActive) {
                                Thread.sleep(1)
                                val m = Message()
                                m.what = 0
                                m.obj = (SystemClock.uptimeMillis() - timeStart)

                                timerUIHandler.sendMessage(m)
                            }
                        }).start()
                    }
                    v?.onTouchEvent(event) ?: true
                }
                else -> {
                    Log.d("Event", event.action.toString())
                    false
                }
            }
        }
    }
}