package com.jason.daisy.viewsolves

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.daisy.MainActivity
import com.jason.daisy.databinding.ActivityViewSolvesBinding
import kotlinx.coroutines.launch

class ViewSolvesActivity : AppCompatActivity() {
    private lateinit var vBinding : ActivityViewSolvesBinding
    private lateinit var vm : ViewSolvesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityViewSolvesBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        vm = ViewModelProvider(this, ViewSolvesViewModelFactory(application)).get(ViewSolvesViewModel::class.java)

        vBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            vBinding.recyclerView.adapter = SolvesAdapter(vm.getSolves())
        }

        vBinding.recyclerView.setHasFixedSize(true)

        vBinding.viewTimerButton.setOnClickListener { changeScreen() }
    }

    private fun changeScreen() {
        val viewTimerIntent = Intent(this, MainActivity::class.java).apply{}
        startActivity(viewTimerIntent)
    }
}