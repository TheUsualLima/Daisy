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

class ViewSolvesActivity : AppCompatActivity(), SolvesAdapterListener {
    private lateinit var vBinding: ActivityViewSolvesBinding
    private lateinit var vm: ViewSolvesViewModel
    private lateinit var adapter: SolvesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityViewSolvesBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        vm = ViewModelProvider(this, ViewSolvesViewModelFactory(application)).get(ViewSolvesViewModel::class.java)

        vBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SolvesAdapter(this)
        vBinding.recyclerView.adapter = adapter
        populateAdapter()
        vBinding.recyclerView.setHasFixedSize(true)

        vBinding.viewTimerButton.setOnClickListener { changeScreen() }
        vBinding.deleteAllButton.setOnClickListener { showDialog() }

        vm.data.observe(this,
                {
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                }
        )
    }

    private fun changeScreen() {
        val viewTimerIntent = Intent(this, MainActivity::class.java).apply {}
        startActivity(viewTimerIntent)
    }

    override fun populateAdapter() {
        vm.updateSolves()
    }

    override fun deleteDialog(s: Solve) = lifecycleScope.launch {
        vm.delete(s)
        populateAdapter()
    }

    private fun showDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Solves")
        builder.setMessage("This action will delete all solves. Continue?")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> lifecycleScope.launch {
                    vm.deleteAll()
                    populateAdapter()
                }
                else -> Log.d("DELETEALLSOLVES", "CANCELLED")
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

    private fun showDialog(currentItem: Solve) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Solve")
        builder.setMessage("Are you sure you want to delete this solve?")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> vm.delete(currentItem)
                else -> Log.d("DELETESOLVE", "CANCELLED")
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }
}