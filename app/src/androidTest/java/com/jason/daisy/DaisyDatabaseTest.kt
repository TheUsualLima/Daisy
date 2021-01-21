package com.jason.daisy

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import com.jason.daisy.database.SolveDao
import com.jason.daisy.scrambletimer.PuzzleType
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DaisyDatabaseTest {

    private lateinit var solveDao: SolveDao
    private lateinit var db: DaisyDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, DaisyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        solveDao = db.solveDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetSolve() = runBlocking {
        val solve = Solve(PuzzleType.ThreeByThree.puzzleName,"8.16", LocalDateTime.now().toString(), "")
        val solve2 = Solve(PuzzleType.ThreeByThree.puzzleName, "13.37", LocalDateTime.now().toString(), "")
        solveDao.insertAll(solve, solve2)
        val allSolves = solveDao.getAll()
        val s = allSolves.contains(solve)
        Log.d("DATABASE TEST", allSolves.toString())
        assertTrue(s)
    }

    @Test
    @Throws(Exception::class)
    fun getAllOfPuzzleType() = runBlocking {
        val solve = Solve(PuzzleType.ThreeByThree.puzzleName,"8.16", LocalDateTime.now().toString(), "")
        val solve2 = Solve(PuzzleType.FourByFour.puzzleName, "13.37", LocalDateTime.now().toString(), "")
        val solve3 = Solve(PuzzleType.ThreeByThree.puzzleName, "9.99", LocalDateTime.now().toString(), "")
        val solve4 = Solve(PuzzleType.Pyraminx.puzzleName, "5.52", LocalDateTime.now().toString(), "")
        val solve5 = Solve(PuzzleType.ThreeByThree.puzzleName, "11.23", LocalDateTime.now().toString(), "")

        val threeSolves = listOf(solve, solve3, solve5)

        solveDao.insertAll(solve, solve2, solve3, solve4, solve5)

        val solves = solveDao.getAllOfPuzzleType("Three by Three")

        assertEquals(threeSolves, solves)
    }
}