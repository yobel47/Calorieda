package com.bell.calorieda.ui.main.drink

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.Drink
import kotlinx.coroutines.launch

class DrinkViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val drink: Drink = dataRepository.getDrink()

    val drinkData: LiveData<Drink> = dataRepository.getDrinkLiveData()

    fun insert(drink: Drink){
        viewModelScope.launch {
            dataRepository.insertDrink(drink)
        }
    }

    fun updateDrink(id: Int, ml: String, progress: String) = viewModelScope.launch {
        dataRepository.updateDrink(id,ml,progress)
    }
}