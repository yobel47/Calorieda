package com.bell.calorieda.ui.main.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.data.entity.MealSchedule
import kotlinx.coroutines.launch

class MealViewModel(private val localRepository: DataRepository)  : ViewModel() {

    val meal: Meal = localRepository.getLatestMeal()

    fun getMeal(date: String): LiveData<List<Meal>> = localRepository.getMeal(date)
    fun getMealSchedule(): List<MealSchedule> = localRepository.getMealScheduleList()
    fun getMealbyDate(date: String): List<Meal> = localRepository.getMealbyDate(date)

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            localRepository.insertMeal(meal)
        }
    }

    fun updateStatus(status: String, date: String, oldMeal: String) = viewModelScope.launch {
        localRepository.updateMealStatus(status, date, oldMeal)
    }
}