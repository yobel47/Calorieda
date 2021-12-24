package com.bell.calorieda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bell.calorieda.data.entity.Calories

@Dao
interface CaloriesDao {
    @Query("SELECT * FROM calories ORDER BY id DESC LIMIT 1")
    fun getLastestCaloriesLiveData(): LiveData<Calories>

    @Query("SELECT * FROM calories ORDER BY id DESC LIMIT 1")
    fun getLastestCalories(): Calories

    @Query("SELECT * FROM calories WHERE date = :date")
    fun getCaloriesByDate(date: String): Calories

    @Query("SELECT * FROM calories WHERE date = :date")
    fun getCaloriesByDateLiveData(date: String): LiveData<List<Calories>>

    @Insert
    fun insertCalories(calories: Calories): Long

    @Query("UPDATE calories SET kcal = :kcal WHERE date = :date")
    fun updateCalories(kcal: String, date: String)

    @Query("UPDATE calories SET kcal = 0 WHERE date = :date")
    fun resetCalories(date: String)
}