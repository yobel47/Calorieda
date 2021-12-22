package com.bell.calorieda.ui.drinkSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.DrinkSchedule
import kotlinx.coroutines.launch

class DrinkScheduleViewModel(private val dataRepository: DataRepository)  : ViewModel() {

    fun getDrinkSchedule(): LiveData<List<DrinkSchedule>> = dataRepository.getDrinkSchedule()

    fun insert(drinkSchedule: DrinkSchedule){
        viewModelScope.launch {
            dataRepository.insertDrinkSchedule(drinkSchedule)
        }
    }

    fun updateDrinkSchedule(id: Int, time: String) = viewModelScope.launch {
        dataRepository.updateDrinkSchedule(id,time)
    }

    fun delete(id: Int) {
        dataRepository.deleteDrinkSchedule(id)
    }

}