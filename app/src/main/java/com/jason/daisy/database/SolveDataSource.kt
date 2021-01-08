package com.jason.daisy.database

internal class SolveDataSource (private val db: DaisyDatabase) {
    suspend fun getSolves() : List<Solve> = db.solveDao.getAll()
}