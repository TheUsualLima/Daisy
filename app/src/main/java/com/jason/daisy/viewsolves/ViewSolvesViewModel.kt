package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.ViewModel
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve

class ViewSolvesViewModel(application: Application) : ViewModel() {
    private val db = DaisyDatabase.getInstance(application.applicationContext)

    suspend fun getSolves(): List<Solve> {
        var data = listOf<Solve>()
        data = db.solveDao.getAll()
        return data.reversed()
    }

    suspend fun deleteAll() {
        db.solveDao.deleteAll()
    }

    suspend fun delete(s: Solve) {
        db.solveDao.delete(s)
    }
}