package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.ViewModel
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.runBlocking

class ViewSolvesViewModel(application: Application) : ViewModel() {
    private val db = DaisyDatabase.getInstance(application.applicationContext)

    fun getSolves(): List<Solve> {
        var data = listOf<Solve>()
        runBlocking {
            data = db.solveDao.getAll()
        }
        return data
    }
}