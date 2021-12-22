package com.bell.calorieda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bell.calorieda.data.entity.DrinkSchedule

@Dao
interface DrinkScheduleDao {
    @Query("SELECT * FROM drinkschedule ORDER BY time")
    fun getDrinkSchedule(): LiveData<List<DrinkSchedule>>

    @Query("SELECT * FROM drinkschedule ORDER BY time")
    fun getDrinkScheduleList(): List<DrinkSchedule>

    @Insert
    fun insertDrinkSchedule(drinkschedule: DrinkSchedule): Long

    @Query("DELETE FROM drinkschedule WHERE id = :id")
    fun deleteDrinkSchedule(id: Int)

    @Query("UPDATE drinkschedule SET time = :time WHERE id = :id")
    fun updateDrinkSchedule(id: Int,time: String)
}