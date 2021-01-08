package com.jason.daisy.viewsolves

import android.app.Application
import androidx.lifecycle.ViewModel
import com.jason.daisy.database.DaisyDatabase

class ViewSolvesViewModel(application: Application) : ViewModel() {
    private val db = DaisyDatabase.getInstance(application.applicationContext)


}