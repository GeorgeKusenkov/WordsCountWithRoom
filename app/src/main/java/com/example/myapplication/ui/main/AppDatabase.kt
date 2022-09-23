package com.example.myapplication.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dictionary::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}