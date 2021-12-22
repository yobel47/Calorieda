package com.bell.calorieda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bell.calorieda.data.dao.*
import com.bell.calorieda.data.entity.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

@Database(
    entities = [Drink::class, Meal::class, MealSchedule::class, DrinkSchedule::class, Calories::class],
    version = 1,
    exportSchema = false
)
abstract class CaloriedaDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao
    abstract fun mealDao(): MealDao
    abstract fun mealScheduleDao(): MealScheduleDao
    abstract fun drinkScheduleDao(): DrinkScheduleDao
    abstract fun caloriesDao(): CaloriesDao

    companion object {
        @Volatile
        private var INSTANCE: CaloriedaDatabase? = null
        fun getInstance(context: Context): CaloriedaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaloriedaDatabase::class.java,
                    "task.db"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Executors.newSingleThreadScheduledExecutor().execute {
                            INSTANCE?.fillWithStartingData(
                                INSTANCE!!.mealDao(),
                                INSTANCE!!.drinkDao(),
                                INSTANCE!!.mealScheduleDao(),
                                INSTANCE!!.drinkScheduleDao(),
                                INSTANCE!!.caloriesDao()
                            )
                        }
                    }
                }).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private fun fillWithStartingData(
        meal: MealDao,
        drink: DrinkDao,
        mealSchedule: MealScheduleDao,
        drinkSchedule: DrinkScheduleDao,
        calories: CaloriesDao
    ) {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateNow = sdf.format(calendar.time)
        drink.insertDrink(
            Drink(ml = "0", progress = "0", target = "1000", date = dateNow)
        )
        mealSchedule.insertMealSchedule(MealSchedule(meal = "Breakfast", time = "08:00"))
        mealSchedule.insertMealSchedule(MealSchedule(meal = "Lunch", time = "13:00"))
        mealSchedule.insertMealSchedule(MealSchedule(meal = "Dinner", time = "19:00"))
        meal.insertMeal(Meal(meal = "Breakfast", time = "08:00", status = "0", date = dateNow))
        meal.insertMeal(Meal(meal = "Lunch", time = "13:00", status = "0", date = dateNow))
        meal.insertMeal(Meal(meal = "Dinner", time = "19:00", status = "0", date = dateNow))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "07:00"))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "10:00"))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "13:00"))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "16:00"))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "18:00"))
        drinkSchedule.insertDrinkSchedule(DrinkSchedule(time = "21:00"))
        calories.insertCalories(Calories(kcal = "0", date = dateNow))
    }

}