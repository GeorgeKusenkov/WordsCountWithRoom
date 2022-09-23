package com.example.myapplication.ui.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM dictionary")
    fun getAll(): Flow<List<Dictionary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dictionary: Dictionary)

    @Query("UPDATE dictionary SET count = count + 1 WHERE text GLOB :text")
    suspend fun updateCount(text: String)

    @Query("SELECT * FROM dictionary ORDER BY count DESC LIMIT 5")
    fun getSorted(): Flow<List<Dictionary>>

    @Query("DELETE FROM dictionary")
    suspend fun delete()
}