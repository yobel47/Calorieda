package com.bell.calorieda.ui.mealSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.data.entity.MealSchedule
import kotlinx.coroutines.launch

class MealScheduleViewModel(private val dataRepository: DataRepository)  : ViewModel() {

    fun getMealSchedule(): LiveData<List<MealSchedule>> = dataRepository.getMealSchedule()

    fun insertMealSchedule(mealSchedule: MealSchedule){
        viewModelScope.launch {
            dataRepository.insertMealSchedule(mealSchedule)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            dataRepository.insertMeal(meal)
        }
    }

    fun updateMealSchedule(id: Int, name: String, time: String) = viewModelScope.launch {
        dataRepository.updateMealSchedule(id,name,time)
    }

    fun updateMeal(name: String, time: String, date: String, oldMeal: String) = viewModelScope.launch {
        dataRepository.updateMeal(name,time,date,oldMeal)
    }

    fun deleteMealSchedule(id: Int) {
        dataRepository.deleteMealSchedule(id)
    }

    fun deleteMeal(oldMeal: String, date: String) {
        dataRepository.deleteMeal(oldMeal,date)
    }
}