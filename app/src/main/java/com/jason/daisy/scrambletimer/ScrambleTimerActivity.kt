package com.jason.daisy.scrambletimer

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jason.daisy.databinding.ActivityMainBinding
import com.jason.daisy.viewsolves.ViewSolvesActivity

class ScrambleTimerActivity : AppCompatActivity() {
    private lateinit var vBinding : ActivityMainBinding
    private lateinit var vm : ScrambleTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        vm = ViewModelProvider(this, ScrambleTimerViewModelFactory(application)).get(
            ScrambleTimerViewModel::class.java)

        vm.currentTime.observe(this, { vBinding.timerTextView.text = it })
        vm.timerColor.observe(this, { vBinding.timerTextView.setTextColor(it) })
        vm.scramble.observe(this, { vBinding.scrambleTextView.text = it })

        vBinding.lunaButton.setOnClickListener {
            Toast.makeText(vBinding.lunaButton.context, "I Love you, Luna", Toast.LENGTH_SHORT).show()
        }

        vBinding.viewSolvesButton.setOnClickListener {
            changeScreen()
        }

        vBinding.mainLayout.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> !vm.handleActionDown()
                MotionEvent.ACTION_UP -> {
                    vBinding.mainLayout.performClick()
                    !vm.handleActionUp()
                }
                else -> true
            }
        }
    }

    private fun changeScreen() {
        val viewSolvesIntent = Intent(this, ViewSolvesActivity::class.java).apply{}
        startActivity(viewSolvesIntent)
    }
}