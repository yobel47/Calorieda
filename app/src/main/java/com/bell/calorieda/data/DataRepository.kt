package com.bell.calorieda.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.bell.calorieda.data.dao.*
import com.bell.calorieda.data.entity.*

class DataRepository(
    private val drinkDao: DrinkDao,
    private val mealDao: MealDao,
    private val mealScheduleDao: MealScheduleDao,
    private val drinkScheduleDao: DrinkScheduleDao,
    private val caloriesDao: CaloriesDao
) {

    companion object {

        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(context: Context): DataRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = CaloriedaDatabase.getInstance(context)
                    instance = DataRepository(
                        database.drinkDao(),
                        database.mealDao(),
                        database.mealScheduleDao(),
                        database.drinkScheduleDao(),
                        database.caloriesDao()
                    )
                }
                return instance as DataRepository
            }

        }
    }

    //Meal
    fun getLatestMeal(): Meal {
        return mealDao.getLatestMeal()
    }

    fun getMeal(date: String): LiveData<List<Meal>> {
        return mealDao.getMeal(date)
    }

    fun getMealbyDate(date: String): List<Meal> {
        return mealDao.getMealbyDate(date)
    }

    fun getMealbyDateStatus(date: String): LiveData<List<Meal>> {
        return mealDao.getMealbyDateStatus(date)
    }

    fun insertMeal(newMeal: Meal): Long {
        return mealDao.insertMeal(newMeal)
    }

    fun updateMeal(meal: String, time: String, date: String, oldMeal: String) {
        mealDao.updateMeal(meal, time, date, oldMeal)
    }

    fun updateMealStatus(status: String, date: String, oldMeal: String) {
        mealDao.updateStatus(status, date, oldMeal)
    }

    fun deleteMeal(oldMeal: String, date: String) {
        mealDao.deleteMeal(oldMeal, date)
    }

    //Drink
    fun getDrink(): Drink {
        return drinkDao.getDrink()
    }

    fun getDrinkLiveData(): LiveData<Drink> {
        return drinkDao.getDrinkLiveData()
    }

    fun getDrinkbyDate(date: String): LiveData<List<Drink>> {
        return drinkDao.getDrinkbyDate(date)
    }

    fun insertDrink(newDrink: Drink): Long {
        return drinkDao.insertDrink(newDrink)
    }

    fun updateDrink(id: Int, ml: String, progress: String) {
        drinkDao.updateDrink(id, ml, progress)
    }

    fun updateTarget(id: Int, target: String) {
        drinkDao.updateTarget(id, target)
    }

    fun resetDrink(date: String) {
        drinkDao.resetDrink(date)
    }


    //Meal Schedule
    fun getMealSchedule(): LiveData<List<MealSchedule>> {
        return mealScheduleDao.getMealSchedule()
    }

    fun getMealScheduleList(): List<MealSchedule> {
        return mealScheduleDao.getMealScheduleList()
    }

    fun insertMealSchedule(newMealSchedule: MealSchedule): Long {
        return mealScheduleDao.insertMealSchedule(newMealSchedule)
    }

    fun updateMealSchedule(id: Int, name: String, time: String) {
        mealScheduleDao.updateMealSchedule(id, name, time)
    }

    fun deleteMealSchedule(id: Int) {
        mealScheduleDao.deleteMealSchedule(id)
    }

    //Drink Schedule
    fun getDrinkSchedule(): LiveData<List<DrinkSchedule>> {
        return drinkScheduleDao.getDrinkSchedule()
    }

    fun getDrinkScheduleList(): List<DrinkSchedule> {
        return drinkScheduleDao.getDrinkScheduleList()
    }

    fun insertDrinkSchedule(newDrinkSchedule: DrinkSchedule): Long {
        return drinkScheduleDao.insertDrinkSchedule(newDrinkSchedule)
    }

    fun updateDrinkSchedule(id: Int, time: String) {
        drinkScheduleDao.updateDrinkSchedule(id, time)
    }

    fun deleteDrinkSchedule(id: Int) {
        drinkScheduleDao.deleteDrinkSchedule(id)
    }

    //Calories
    fun getCaloriesByDate(date: String): Calories {
        return caloriesDao.getCaloriesByDate(date)
    }

    fun getCaloriesByDateLiveData(date: String): LiveData<List<Calories>> {
        return caloriesDao.getCaloriesByDateLiveData(date)
    }

    fun getLastestCaloriesLivedata(): LiveData<Calories> {
        return caloriesDao.getLastestCaloriesLiveData()
    }

    fun getLastestCalories(): Calories {
        return caloriesDao.getLastestCalories()
    }

    fun insertCalories(newCalories: Calories): Long {
        return caloriesDao.insertCalories(newCalories)
    }

    fun updateCalories(kcal: String, date: String) {
        caloriesDao.updateCalories(kcal, date)
    }

    fun resetCalories(date: String) {
        caloriesDao.resetCalories(date)
    }

}