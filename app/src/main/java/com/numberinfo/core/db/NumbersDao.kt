package com.numberinfo.core.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {

    @Insert
    suspend fun insertNumber(number: Numbers)

    @Query("Select * from numbers_table")
    fun gelAllNumbers(): Flow<List<Numbers>>
}