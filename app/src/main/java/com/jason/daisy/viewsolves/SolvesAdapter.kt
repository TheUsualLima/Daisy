package com.jason.daisy.viewsolves

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.SolveItemBinding

class SolvesAdapter(private val listener: SolvesAdapterListener) : RecyclerView.Adapter<SolvesAdapter.SolvesViewHolder>() {
    private val solveList = listener.getSolves()

    override fun getItemCount() = solveList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvesViewHolder {
        val itemBinding = SolveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SolvesViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: SolvesViewHolder, position: Int) {
        val currentItem = solveList[position]

        holder.bind(currentItem)
    }

    class SolvesViewHolder(private val itemBinding: SolveItemBinding, private val listener: SolvesAdapterListener) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currentItem: Solve) {
            itemBinding.apply {
                textView1.text = currentItem.time
                val s = "Date: ${currentItem.dateOfSolve.substring(0..9)} Time: ${currentItem.dateOfSolve.substring(11 until currentItem.dateOfSolve.length - 1)}"
                textView2.text = s
                deleteSolveButton.setOnClickListener {
                    //Interface to call a function in the activity from adapter
                    showDialog(currentItem)
                }
            }
        }

        private fun showDialog(currentItem: Solve) {
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(itemBinding.root.context)
            builder.setTitle("Delete Solve")
            builder.setMessage("Are you sure you want to delete this solve?")
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> listener.delete(currentItem)
                    else -> Log.d("DELETESOLVE", "CANCELLED")
                }
            }
            builder.setPositiveButton("YES", dialogClickListener)
            builder.setNegativeButton("NO", dialogClickListener)

            dialog = builder.create()
            dialog.show()
        }
    }
}