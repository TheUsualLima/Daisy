package com.jason.daisy.viewsolves

import com.jason.daisy.database.Solve
import kotlinx.coroutines.Job

interface SolvesAdapterListener {
    fun deleteDialog(s : Solve): Job
}