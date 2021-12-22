package com.bell.calorieda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bell.calorieda.data.entity.MealSchedule

@Dao
interface MealScheduleDao {
    @Query("SELECT * FROM mealschedule ORDER BY time")
    fun getMealSchedule(): LiveData<List<MealSchedule>>

    @Query("SELECT * FROM mealschedule ORDER BY time")
    fun getMealScheduleList(): List<MealSchedule>

    @Insert
    fun insertMealSchedule(mealSchedule: MealSchedule): Long

    @Query("DELETE FROM mealschedule WHERE id = :id")
    fun deleteMealSchedule(id: Int)

    @Query("UPDATE mealschedule SET meal = :name, time = :time WHERE id = :id")
    fun updateMealSchedule(id: Int,name: String, time: String)
}