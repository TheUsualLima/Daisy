package com.jason.daisy.viewsolves

import androidx.recyclerview.widget.DiffUtil
import com.jason.daisy.database.Solve

object SolveDiffUtilItemCallback : DiffUtil.ItemCallback<Solve>() {
    override fun areItemsTheSame(oldItem: Solve, newItem: Solve): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Solve, newItem: Solve): Boolean {
        return oldItem == newItem
    }
}
