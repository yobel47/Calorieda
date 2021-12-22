package com.bell.calorieda.ui.main.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Calories
import com.bell.calorieda.data.entity.Drink
import com.bell.calorieda.data.entity.Meal

class ReportViewModel(private val dataRepository: DataRepository)  : ViewModel() {

    fun getMealbyDateStatus(date: String): LiveData<List<Meal>> = dataRepository.getMealbyDateStatus(date)
    fun getDrinkbyDate(date: String): LiveData<List<Drink>> = dataRepository.getDrinkbyDate(date)
    fun getCaloriesData(date: String): LiveData<List<Calories>> = dataRepository.getCaloriesByDateLiveData(date)

    fun getData(date: String): LiveData<List<Drink>> {
        return dataRepository.getDrinkbyDate(date)
    }

}