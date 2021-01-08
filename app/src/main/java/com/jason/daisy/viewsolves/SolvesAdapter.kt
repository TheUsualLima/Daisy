package com.jason.daisy.viewsolves

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.SolveItemBinding

class SolvesAdapter(private val solveList: List<Solve>) : RecyclerView.Adapter<SolvesAdapter.SolvesViewHolder>() {
    override fun getItemCount() = solveList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvesViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val cellForRow = layoutInflater.inflate(R.layout.solve_item, parent, false)
//        return SolvesViewHolder(cellForRow)
        val itemBinding = SolveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SolvesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SolvesViewHolder, position: Int) {
        val currentItem = solveList[position]

        holder.bind(currentItem)
    }

    class SolvesViewHolder(private val itemBinding: SolveItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currentItem: Solve) {
            itemBinding.apply {
                textView1.text = currentItem.time
                textView2.text = currentItem.dateOfSolve
            }
        }
    }
}