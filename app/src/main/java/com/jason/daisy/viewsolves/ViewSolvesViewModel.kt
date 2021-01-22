package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewSolvesViewModel(application: Application, private val puzzleType: String) : ViewModel() {
    private val db = DaisyDatabase.getInstance(application.applicationContext)

    private val _data : MutableLiveData<List<Solve>> = MutableLiveData()
    val data : LiveData<List<Solve>> = _data


    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateSolves()
        }
    }

    private suspend fun updateSolves() {
        _data.postValue(db.solveDao.getAllOfPuzzleType(puzzleType).reversed())
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            db.solveDao.deleteAll()
            updateSolves()
        }
    }

    fun delete(s: Solve) {
        viewModelScope.launch(Dispatchers.IO) {
            db.solveDao.delete(s)
            updateSolves()
        }
    }
}