package com.jason.daisy

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jason.daisy.database.DaisyDatabase
import com.jason.daisy.database.Solve
import com.jason.daisy.database.SolveDao
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
        val solve = Solve("8.16", LocalDateTime.now().toString(), "")
        val solve2 = Solve("13.37", LocalDateTime.now().toString(), "")
        solveDao.insertAll(solve, solve2)
        val allSolves = solveDao.getAll()
        val s = allSolves.contains(solve)
        Log.d("DATABASE TEST", allSolves.toString())
        assertTrue(s)
    }
}