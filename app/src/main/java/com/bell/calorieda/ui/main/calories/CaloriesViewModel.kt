package com.bell.calorieda.ui.main.calories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Calories
import kotlinx.coroutines.launch

class CaloriesViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val caloriesLiveData: LiveData<Calories> = dataRepository.getLastestCaloriesLivedata()
    val caloriesData: Calories = dataRepository.getLastestCalories()
    fun insert(calories: Calories){
        viewModelScope.launch {
            dataRepository.insertCalories(calories)
        }
    }

}