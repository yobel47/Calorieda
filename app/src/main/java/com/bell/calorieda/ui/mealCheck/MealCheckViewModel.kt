package com.bell.calorieda.ui.mealCheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Meal
import kotlinx.coroutines.launch

class MealCheckViewModel(private val dataRepository: DataRepository)  : ViewModel() {

    val meal: Meal = dataRepository.getLatestMeal()

    fun updateStatus(status: String, date: String, oldMeal: String) = viewModelScope.launch {
        dataRepository.updateMealStatus(status, date, oldMeal)
    }
}