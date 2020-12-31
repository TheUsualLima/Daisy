package com.jason.daisy

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jason.daisy.activities.ViewSolves
import com.jason.daisy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currentTime.observe(this, { binding.textView.text = it.toString() })
        viewModel.timerColor.observe(this, { binding.textView.setTextColor(it) })

        binding.lunaButton.setOnClickListener {
            Toast.makeText(binding.lunaButton.context, "I Love you, Luna", Toast.LENGTH_SHORT).show()
        }

        binding.viewSolvesButton.setOnClickListener {
            changeScreen()
        }

        binding.mainLayout.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> !viewModel.handleActionDown()
                MotionEvent.ACTION_UP -> {
                    binding.mainLayout.performClick()
                    !viewModel.handleActionUp()
                }
                else -> true
            }
        }
    }

    private fun changeScreen() {
        val viewSolvesIntent = Intent(this, ViewSolves::class.java).apply{}
        startActivity(viewSolvesIntent)
    }
}