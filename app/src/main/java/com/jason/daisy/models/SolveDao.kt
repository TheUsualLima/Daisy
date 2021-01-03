package com.jason.daisy.models

import androidx.room.*

@Dao
interface SolveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg solves: Solve)

    @Delete
    fun delete(solve: Solve)

    @Query("SELECT * FROM solves")
    fun getAll(): List<Solve>
}