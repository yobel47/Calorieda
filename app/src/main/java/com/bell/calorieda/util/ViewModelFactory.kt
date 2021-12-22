package com.bell.calorieda.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.ui.main.drink.DrinkViewModel
import com.bell.calorieda.ui.main.meal.MealViewModel
import com.bell.calorieda.ui.main.report.ReportViewModel
import com.bell.calorieda.ui.mealSchedule.MealScheduleViewModel
import com.bell.calorieda.ui.setting.SettingViewModel
import com.bell.calorieda.ui.drinkSchedule.DrinkScheduleViewModel
import com.bell.calorieda.ui.main.calories.CaloriesViewModel
import com.bell.calorieda.ui.mealCheck.MealCheckViewModel
import com.bell.calorieda.ui.nutrient.NutrientViewModel

class ViewModelFactory private constructor(private val dataRepository: DataRepository) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    DataRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DrinkViewModel::class.java) -> {
                DrinkViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(MealViewModel::class.java) -> {
                MealViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(MealScheduleViewModel::class.java) -> {
                MealScheduleViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(DrinkScheduleViewModel::class.java) -> {
                DrinkScheduleViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(CaloriesViewModel::class.java) -> {
                CaloriesViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(NutrientViewModel::class.java) -> {
                NutrientViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(MealCheckViewModel::class.java) -> {
                MealCheckViewModel(dataRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}