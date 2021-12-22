package com.bell.calorieda.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "meal")
    val meal: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "date")
    val date: String
)