package com.jason.daisy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jason.daisy.MainActivity
import com.jason.daisy.R

class ViewSolves : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_solves)
    }

    fun changeScreen(view: View) {
        val viewTimerIntent = Intent(this, MainActivity::class.java).apply{}
        startActivity(viewTimerIntent)
    }
}