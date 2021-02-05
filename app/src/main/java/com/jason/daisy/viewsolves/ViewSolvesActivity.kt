package com.jason.daisy.viewsolves

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.ActivityViewSolvesBinding
import com.jason.daisy.scrambletimer.ScrambleTimerActivity

class ViewSolvesActivity : AppCompatActivity(), SolvesAdapterListener {
    private lateinit var vBinding: ActivityViewSolvesBinding
    private lateinit var vm: ViewSolvesViewModel
    private lateinit var adapter: SolvesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityViewSolvesBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        val puzzleTypeString = intent.extras?.getString("puzzleType")
        if(puzzleTypeString != null) {
            vm = ViewModelProvider(this, ViewSolvesViewModelFactory(application, puzzleTypeString)).get(ViewSolvesViewModel::class.java)
        } else {
            throw Exception("PuzzleType string not provided")
        }
        setUpRecycler()
        vBinding.viewTimerButton.setOnClickListener { changeScreen() }
        vBinding.deleteAllButton.setOnClickListener { showDeleteAllDialog() }

        vm.data.observe(this, { adapter.submitList(it) })
    }

    private fun setUpRecycler() {
        vBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SolvesAdapter(this)
        vBinding.recyclerView.adapter = adapter
        vBinding.recyclerView.setHasFixedSize(true)

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(vBinding.recyclerView)
    }

    private fun changeScreen() {
        val viewTimerIntent = Intent(this, ScrambleTimerActivity::class.java).apply {}
        startActivity(viewTimerIntent)
        finish()
    }

    override fun deleteSolve(s: Solve) {
        vm.delete(s)
    }

    private fun showDeleteAllDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Solves")
        builder.setMessage("This action will delete all solves. Continue?")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    vm.deleteAll()
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