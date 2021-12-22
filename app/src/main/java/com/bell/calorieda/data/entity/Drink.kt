package com.bell.calorieda.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drink (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "ml")
    val ml: String,
    @ColumnInfo(name = "progress")
    val progress: String,
    @ColumnInfo(name = "target")
    val target: String,
    @ColumnInfo(name = "date")
    val date: String
)