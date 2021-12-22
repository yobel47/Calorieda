package com.bell.calorieda.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Drink
import com.bell.calorieda.data.entity.DrinkSchedule
import com.bell.calorieda.data.entity.MealSchedule
import kotlinx.coroutines.launch

class SettingViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val drink: Drink = dataRepository.getDrink()

    fun meal(): List<MealSchedule> = dataRepository.getMealScheduleList()
    fun drink(): List<DrinkSchedule> = dataRepository.getDrinkScheduleList()

    fun updateTarget(id: Int, target: String) = viewModelScope.launch {
        dataRepository.updateTarget(id,target)
    }

    fun resetDrink(date: String) = viewModelScope.launch {
        dataRepository.resetDrink(date)
    }

    fun resetCalories(date: String) = viewModelScope.launch {
        dataRepository.resetCalories(date)
    }
}