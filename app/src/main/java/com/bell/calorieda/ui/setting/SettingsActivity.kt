package com.bell.calorieda.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.bell.calorieda.R
import com.bell.calorieda.notification.DrinkReminder
import com.bell.calorieda.notification.MealReminder
import com.bell.calorieda.ui.drinkSchedule.DrinkScheduleActivity
import com.bell.calorieda.ui.mealSchedule.MealScheduleActivity
import com.bell.calorieda.util.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Setting"
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val settingViewModel: SettingViewModel
            val factory = ViewModelFactory.getInstance(requireContext())
            settingViewModel =
                ViewModelProvider(this, factory)[SettingViewModel::class.java]

            val intentMealPreference = findPreference<Preference>("mealActivity")
            val intent = Intent(context, MealScheduleActivity::class.java)
            intentMealPreference?.intent = intent

            val intentDrinkPreference = findPreference<Preference>("drinkActivity")
            val intent1 = Intent(context, DrinkScheduleActivity::class.java)
            intentDrinkPreference?.intent = intent1

            val drinkTarget = findPreference<EditTextPreference>("drink_target")
            val drink = settingViewModel.drink
            drinkTarget?.text = drink.target
            drinkTarget?.summary = drink.target + " ml"
            drinkTarget?.setOnPreferenceChangeListener { preference, newValue ->

                val mBuilder = AlertDialog.Builder(preference.context)
                    .setTitle("Update Drink Target")
                    .setMessage("Updating drink target will also reset your drink progress. Do you want to continue")
                    .setPositiveButton("Yes") { _, _ ->
                        settingViewModel.updateTarget(id = drink.id, target = newValue.toString())
                        drinkTarget.summary = "$newValue ml"
                        val calendar = Calendar.getInstance()
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val dateNow = sdf.format(calendar.time)
                        settingViewModel.resetDrink(dateNow)
                        Snackbar.make(requireView(), "Drink target has been updated",Snackbar.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                val mAlertDialog = mBuilder.create()
                mAlertDialog.show()
                true
            }

            val resetDrink = findPreference<Preference>("resetDrink")
            resetDrink?.setOnPreferenceClickListener {
                val mBuilder = AlertDialog.Builder(it.context)
                    .setTitle("Reset Drink Progress")
                    .setMessage("Are you sure want to reset your drink progress for this day?")
                    .setPositiveButton("Yes") { _, _ ->
                        val calendar = Calendar.getInstance()
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val dateNow = sdf.format(calendar.time)
                        settingViewModel.resetDrink(dateNow)
                        view?.let { it1 -> Snackbar.make(it1,"Drink progress has been reseted",Snackbar.LENGTH_SHORT).show() }
                    }
                    .setNegativeButton("No", null)
                val mAlertDialog = mBuilder.create()
                mAlertDialog.show()

                true
            }

            val notifMeal = findPreference<SwitchPreference>("meal_reminder")
            notifMeal?.setOnPreferenceChangeListener { _, _ ->
                val mealReminder = MealReminder()
                val schedule = settingViewModel.meal()
                if (!notifMeal.isChecked) {
                    context?.let {
                        mealReminder.setNotif(it, schedule)
                    }
                } else {
                    context?.let {
                        mealReminder.cancelAlarm(it, schedule)
                    }
                }
                true
            }
            val notifDrink = findPreference<SwitchPreference>("drink_reminder")
            notifDrink?.setOnPreferenceChangeListener { _, _ ->
                val drinkReminder = DrinkReminder()
                val schedule = settingViewModel.drink()
                if (!notifDrink.isChecked) {
                    context?.let {
                        drinkReminder.setNotif(it, schedule)
                    }
                } else {
                    context?.let {
                        drinkReminder.cancelAlarm(it, schedule)
                    }
                }
                true
            }

            val resetCalories = findPreference<Preference>("resetCalories")
            resetCalories?.setOnPreferenceClickListener {
                val mBuilder = AlertDialog.Builder(it.context)
                    .setTitle("Reset Calories Data")
                    .setMessage("Are you sure want to reset your calories data for this day?")
                    .setPositiveButton("Yes") { _, _ ->
                        val calendar = Calendar.getInstance()
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val dateNow = sdf.format(calendar.time)
                        settingViewModel.resetCalories(dateNow)
                        view?.let { it1 -> Snackbar.make(it1,"Calories data has been reseted",Snackbar.LENGTH_SHORT).show() }
                    }
                    .setNegativeButton("No", null)
                val mAlertDialog = mBuilder.create()
                mAlertDialog.show()

                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}