package com.jason.daisy.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Solve::class], version = 1, exportSchema = false)
abstract class DaisyDatabase : RoomDatabase() {

    abstract  val solveDao : SolveDao

    companion object {
        @Volatile
        private var INSTANCE: DaisyDatabase? = null

        fun getInstance(context: Context) : DaisyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DaisyDatabase::class.java,
                        "daisy_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}