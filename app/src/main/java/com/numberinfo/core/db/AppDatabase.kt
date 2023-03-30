package com.numberinfo.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Numbers::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun numbersDao(): NumbersDao
}