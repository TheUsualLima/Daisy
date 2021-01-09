package com.jason.daisy.viewsolves

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.daisy.MainActivity
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.ActivityViewSolvesBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewSolvesActivity : AppCompatActivity(), SolvesAdapterListener {
    private lateinit var vBinding : ActivityViewSolvesBinding
    private lateinit var vm : ViewSolvesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityViewSolvesBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        vm = ViewModelProvider(this, ViewSolvesViewModelFactory(application)).get(ViewSolvesViewModel::class.java)

        vBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        val t = this
        lifecycleScope.launch {
            vBinding.recyclerView.adapter = SolvesAdapter(t)
        }

        vBinding.recyclerView.setHasFixedSize(true)

        vBinding.viewTimerButton.setOnClickListener { changeScreen() }
        vBinding.deleteAllButton.setOnClickListener { showDialog() }
    }

    private fun changeScreen() {
        val viewTimerIntent = Intent(this, MainActivity::class.java).apply{}
        startActivity(viewTimerIntent)
    }

    override fun getSolves(): List<Solve> = runBlocking {
            vm.getSolves()
        }

    override fun delete(s: Solve) = lifecycleScope.launch {
        vm.delete(s)
        updateAdapter()
    }

    override fun updateAdapter() {
        val t = this
        lifecycleScope.launch {
            vBinding.recyclerView.adapter = SolvesAdapter(t)
        }
    }

    private fun showDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Solves")
        builder.setMessage("This action will delete all solves. Continue?")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> lifecycleScope.launch {
                    vm.deleteAll()
                    updateAdapter()
                }
                else -> Log.d("DELETEALLSOLVES", "CANCELLED")
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }
}