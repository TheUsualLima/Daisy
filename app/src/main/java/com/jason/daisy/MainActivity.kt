package com.jason.daisy

import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jason.daisy.activities.ViewSolves
import com.jason.daisy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var timerActive = false //wrapped into a reference object to be modified when captured in a closure

        val timerUIHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when(msg.what) {
                    0 -> {
                        binding.textView.setTextColor(Color.GREEN)
                    }

                    1 -> {
                        val time = if (msg.obj.toString().length < 4) {
                            msg.obj.toString().padStart(4, '0')
                        } else {
                            msg.obj.toString()
                        }
                        val len = time.length
                        val str = "${time.substring(0, len - 3)}.${time.substring(len - 3, len - 1)}"

                        binding.textView.apply {
                            text = str
                            setTextColor(Color.BLACK)
                        }
                    }

                    2 -> {
                        binding.textView .setTextColor(Color.RED)
                    }
                }
            }
        }

        val toast = Toast.makeText(binding.button.context, "I Love you, Luna", Toast.LENGTH_SHORT)
        binding.button.setOnClickListener {
            toast.show()
        }

        binding.mainLayout.setOnTouchListener { v, event ->

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {

                    Log.d("Touch", "ACTION DOWN")
                    if(timerActive) {
                        binding.mainLayout.performClick()
                        timerActive = false
                        timerActive
                    } else {
                        val onMsg : Message = Message()
                        onMsg.what = 0
                        timerUIHandler.sendMessage(onMsg)
                        true
                    }

                }
                MotionEvent.ACTION_UP -> {

                    Log.d("Touch", "ACTION UP")

                    if (!timerActive) {
                        binding.mainLayout.performClick()
                        val timeStart = SystemClock.uptimeMillis()
                        timerActive = true
                        Thread(Runnable {
                            while (timerActive) {
                                Thread.sleep(1)
                                val updateMsg = Message()
                                updateMsg.what = 1
                                updateMsg.obj = (SystemClock.uptimeMillis() - timeStart)

                                timerUIHandler.sendMessage(updateMsg)
                            }
                            val offMsg = Message()
                            offMsg.what = 2
                            timerUIHandler.sendMessage(offMsg)
                        }).start()
                    }
                    v?.onTouchEvent(event) ?: true

                }
                else -> true
            }
        }
    }

    fun changeScreen(view: View) {
        val viewSolvesIntent = Intent(this, ViewSolves::class.java).apply{}
        startActivity(viewSolvesIntent)
    }
}