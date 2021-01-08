package com.jason.daisy.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["time", "date"], tableName = "solves")
data class Solve (
    val time: String,
    @ColumnInfo(name = "date") val dateOfSolve: String
)