package com.example.myapplication.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary (
    @PrimaryKey
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "count") val count: Int
)