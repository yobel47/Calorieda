package com.bell.calorieda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bell.calorieda.data.entity.Drink

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drink ORDER BY id DESC LIMIT 1")
    fun getDrink(): Drink

    @Query("SELECT * FROM drink ORDER BY id DESC LIMIT 1")
    fun getDrinkLiveData(): LiveData<Drink>

    @Query("SELECT * FROM drink WHERE date = :date")
    fun getDrinkbyDate(date: String): LiveData<List<Drink>>

    @Insert
    fun insertDrink(drink: Drink): Long

    @Query("UPDATE drink SET ml = :ml, progress = :progress WHERE id = :id")
    fun updateDrink(id: Int,ml: String, progress: String)

    @Query("UPDATE drink SET target = :target WHERE id = :id")
    fun updateTarget(id: Int, target: String)

    @Query("UPDATE drink SET ml = 0, progress = 0 WHERE date = :date")
    fun resetDrink(date: String)
}