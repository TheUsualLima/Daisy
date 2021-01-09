package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.launch

class ViewSolvesViewModel(application: Application) : ViewModel() {
    private val db = DaisyDatabase.getInstance(application.applicationContext)
    val data : MutableLiveData<List<Solve>> = MutableLiveData()

    fun updateSolves() {
        viewModelScope.launch { data.postValue(db.solveDao.getAll().reversed()) }
    }

    fun deleteAll() {
        viewModelScope.launch { db.solveDao.deleteAll() }
    }

    fun delete(s: Solve) {
        viewModelScope.launch { db.solveDao.delete(s) }
    }
}