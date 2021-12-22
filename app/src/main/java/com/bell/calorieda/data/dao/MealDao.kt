package com.bell.calorieda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bell.calorieda.data.entity.Meal

@Dao
interface MealDao {
    @Query("SELECT * FROM meal WHERE date = :date ORDER BY time")
    fun getMeal(date: String): LiveData<List<Meal>>

    @Query("SELECT * FROM meal ORDER BY id DESC LIMIT 1")
    fun getLatestMeal(): Meal

    @Query("SELECT * FROM meal WHERE date = :date")
    fun getMealbyDate(date: String): List<Meal>

    @Query("SELECT * FROM meal WHERE date = :date AND status != 0")
    fun getMealbyDateStatus(date: String): LiveData<List<Meal>>

    @Insert
    fun insertMeal(meal: Meal): Long

    @Query("UPDATE meal SET meal = :name, time = :time WHERE date = :date AND meal = :oldMeal")
    fun updateMeal(name: String, time: String, date: String, oldMeal: String)

    @Query("UPDATE meal SET status = :status WHERE date = :date AND meal = :oldMeal")
    fun updateStatus(status: String, date: String, oldMeal: String)

    @Query("DELETE FROM meal WHERE meal = :oldMeal AND date = :date")
    fun deleteMeal(oldMeal: String, date: String)
}