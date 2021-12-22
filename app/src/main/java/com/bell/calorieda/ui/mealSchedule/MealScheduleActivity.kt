package com.bell.calorieda.ui.mealSchedule

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.data.entity.MealSchedule
import com.bell.calorieda.databinding.ActivityMealScheduleBinding
import com.bell.calorieda.util.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MealScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealScheduleBinding
    private lateinit var mealScheduleViewModel: MealScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Meal Schedule"

        var adapter: MealScheduleAdapter

        val factory = ViewModelFactory.getInstance(applicationContext)
        mealScheduleViewModel =
            ViewModelProvider(this, factory)[MealScheduleViewModel::class.java]


        mealScheduleViewModel.getMealSchedule().observe(this, {
            adapter = MealScheduleAdapter(it)
            binding.rvMealSchedule.layoutManager = LinearLayoutManager(this)
            binding.rvMealSchedule.setHasFixedSize(true)
            binding.rvMealSchedule.adapter = adapter
        })

        binding.fabAdd.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(it.context).inflate(R.layout.meal_schedule_dialog, null)
            val mBuilder = AlertDialog.Builder(it.context, R.style.CustomAlertDialog)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mDialogView.findViewById<ImageButton>(R.id.btn_close).setOnClickListener {
                mAlertDialog.dismiss()
            }
            val edScheduleName = mDialogView.findViewById<EditText>(R.id.ed_schedule_name)
            edScheduleName.requestFocus()
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val btnScheduleTime = mDialogView.findViewById<ImageButton>(R.id.btn_time_picker)
            val tvScheduleTime = mDialogView.findViewById<TextView>(R.id.tv_time_schedule)
            tvScheduleTime.text = timeFormat.format(calendar.time)
            btnScheduleTime.setOnClickListener { btnView ->
                val timeSplit = tvScheduleTime.text.split(":").toTypedArray()
                val hour = timeSplit[0].trim { hour -> hour <= ' ' }.toInt()
                val minute = timeSplit[1].trim { minute -> minute <= ' ' }.toInt()
                val mTimePicker = TimePickerDialog(
                    btnView.context,
                    { _, selectedHour, selectedMinute ->
                        val timeText = timeFormat.parse("$selectedHour:$selectedMinute")
                        if (timeText != null) {
                            tvScheduleTime.text = timeFormat.format(timeText)
                        }
                    },
                    hour,
                    minute,
                    true
                )
                mTimePicker.show()
            }
            mDialogView.findViewById<Button>(R.id.btn_update).visibility = View.GONE
            mDialogView.findViewById<Button>(R.id.btn_delete).visibility = View.GONE
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateNow = sdf.format(calendar.time)
            val btnAdd = mDialogView.findViewById<Button>(R.id.btn_add)
            btnAdd.visibility = View.VISIBLE
            btnAdd.setOnClickListener {
                mealScheduleViewModel.insertMealSchedule(
                    MealSchedule(
                        meal = edScheduleName.text.toString(),
                        time = tvScheduleTime.text.toString()
                    )
                )
                mealScheduleViewModel.insertMeal(
                    Meal(
                        meal = edScheduleName.text.toString(),
                        time = tvScheduleTime.text.toString(),
                        status = "0",
                        date = dateNow
                    )
                )
                val sn = Snackbar.make(binding.root,"Meal schedule added",Snackbar.LENGTH_SHORT)
                sn.anchorView = binding.fabAdd
                sn.show()
                mAlertDialog.dismiss()
            }
        }
    }
}