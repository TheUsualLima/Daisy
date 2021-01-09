package com.jason.daisy.viewsolves

import com.jason.daisy.database.Solve
import kotlinx.coroutines.Job

interface SolvesAdapterListener {
    fun delete(s : Solve): Job
    fun updateAdapter()
    fun getSolves(): List<Solve>
}