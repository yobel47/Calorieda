package com.bell.calorieda.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calories (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "kcal")
    val kcal: String,
    @ColumnInfo(name = "date")
    val date: String
)