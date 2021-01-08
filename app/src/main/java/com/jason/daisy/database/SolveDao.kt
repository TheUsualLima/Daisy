package com.jason.daisy.database

import androidx.room.*

@Dao
interface SolveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg solves: Solve)

    @Delete
    suspend fun delete(solve: Solve)

    @Query("SELECT * FROM solves")
    suspend fun getAll(): List<Solve>

    @Query("DELETE FROM solves")
    suspend fun deleteAll()
}