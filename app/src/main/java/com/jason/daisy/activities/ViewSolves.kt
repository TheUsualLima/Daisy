package com.jason.daisy.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.daisy.MainActivity
import com.jason.daisy.databinding.ActivityViewSolvesBinding

class ViewSolves : AppCompatActivity() {
    private lateinit var binding : ActivityViewSolvesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSolvesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SolvesAdapter()

        binding.viewTimerButton.setOnClickListener { changeScreen() }
    }

    private fun changeScreen() {
        val viewTimerIntent = Intent(this, MainActivity::class.java).apply{}
        startActivity(viewTimerIntent)
    }
}