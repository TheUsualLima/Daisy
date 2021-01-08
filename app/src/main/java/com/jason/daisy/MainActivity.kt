package com.jason.daisy

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jason.daisy.databinding.ActivityMainBinding
import com.jason.daisy.viewsolves.ViewSolvesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var vBinding : ActivityMainBinding
    private lateinit var vm : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        vm = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)

        vm.currentTime.observe(this, { vBinding.textView.text = it })
        vm.timerColor.observe(this, { vBinding.textView.setTextColor(it) })

        vBinding.lunaButton.setOnClickListener {
            vm.clearDb()
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