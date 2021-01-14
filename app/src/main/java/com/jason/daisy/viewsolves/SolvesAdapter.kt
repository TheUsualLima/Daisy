package com.jason.daisy.viewsolves

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.SolveItemBinding

class SolvesAdapter(private val listener: SolvesAdapterListener) : ListAdapter<Solve, SolvesAdapter.SolvesViewHolder>(SolveDiffUtilItemCallback) {

    override fun onBindViewHolder(holder: SolvesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvesViewHolder {
        val itemBinding = SolveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SolvesViewHolder(itemBinding, listener)
    }

    class SolvesViewHolder(private val itemBinding: SolveItemBinding, private val listener: SolvesAdapterListener) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currentItem: Solve) {
            itemBinding.apply {
                textView1.text = currentItem.time
                val s = "Date: ${currentItem.dateOfSolve.substring(0..9)} Time: ${currentItem.dateOfSolve.substring(11 until currentItem.dateOfSolve.length - 1)}"
                textView2.text = s
                scrambleTextView.text = currentItem.scramble
                deleteSolveButton.setOnClickListener {
                    //Interface to call a function in the activity from adapter
                    listener.deleteDialog(currentItem)
                }
            }
        }
    }
}