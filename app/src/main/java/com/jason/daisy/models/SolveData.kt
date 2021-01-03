package com.jason.daisy.models

import java.time.LocalDateTime

class SolveData {
    companion object {
        fun createDataSet() : ArrayList<Solve> {
            val list = ArrayList<Solve>()
            list.add(Solve("", LocalDateTime.now().toString()))
            return list
        }
    }
}