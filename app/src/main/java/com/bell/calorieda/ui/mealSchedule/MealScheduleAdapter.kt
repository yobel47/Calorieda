package com.bell.calorieda.ui.mealSchedule

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.MealSchedule
import com.bell.calorieda.databinding.ItemMealScheduleBinding
import com.bell.calorieda.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MealScheduleAdapter(private val listMealSchedule: List<MealSchedule>) :
    RecyclerView.Adapter<MealScheduleAdapter.ViewHolder>() {
    private lateinit var mealScheduleViewModel: MealScheduleViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMealScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, meal, time) = listMealSchedule[position]
        holder.binding.tvMealSchedule.text = meal
        holder.binding.tvTimeSchedule.text = time
        holder.itemView.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(it.context).inflate(R.layout.meal_schedule_dialog, null)
            val mBuilder = AlertDialog.Builder(it.context, R.style.CustomAlertDialog)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mDialogView.findViewById<ImageButton>(R.id.btn_close).setOnClickListener {
                mAlertDialog.dismiss()
            }
            val edScheduleName = mDialogView.findViewById<EditText>(R.id.ed_schedule_name)
            edScheduleName.setText(meal)
            val btnScheduleTime = mDialogView.findViewById<ImageButton>(R.id.btn_time_picker)
            val tvScheduleTime = mDialogView.findViewById<TextView>(R.id.tv_time_schedule)
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateNow = sdf.format(calendar.time)
            tvScheduleTime.text = time
            btnScheduleTime.setOnClickListener { btnView ->
                val timeSplit = time.split(":").toTypedArray()
                val hour = timeSplit[0].trim { hour -> hour <= ' ' }.toInt()
                val minute = timeSplit[1].trim { minute -> minute <= ' ' }.toInt()
                val mTimePicker = TimePickerDialog(
                    btnView.context,
                    { _, selectedHour, selectedMinute ->
                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
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
            val btnUpdateSchedule = mDialogView.findViewById<Button>(R.id.btn_update)
            val btnDeleteSchedule = mDialogView.findViewById<Button>(R.id.btn_delete)
            val factory = ViewModelFactory.getInstance(it.context)
            mealScheduleViewModel =
                ViewModelProvider(
                    it.findViewTreeViewModelStoreOwner()!!,
                    factory
                )[MealScheduleViewModel::class.java]
            btnUpdateSchedule.setOnClickListener {
                mealScheduleViewModel.updateMealSchedule(
                    id = id,
                    name = edScheduleName.text.toString(),
                    time = tvScheduleTime.text.toString()
                )
                mealScheduleViewModel.updateMeal(
                    name = edScheduleName.text.toString(),
                    time = tvScheduleTime.text.toString(),
                    date = dateNow,
                    oldMeal = meal
                )
                val sn = Snackbar.make(holder.itemView,"Meal schedule updated", Snackbar.LENGTH_SHORT)
                sn.anchorView = holder.itemView.rootView.findViewById(R.id.fab_add)
                sn.show()
                mAlertDialog.dismiss()
            }
            btnDeleteSchedule.setOnClickListener {
                mealScheduleViewModel.deleteMealSchedule(id)
                mealScheduleViewModel.deleteMeal(meal,dateNow)
                val sn = Snackbar.make(holder.itemView,"Meal schedule deleted", Snackbar.LENGTH_SHORT)
                sn.anchorView = holder.itemView.rootView.findViewById(R.id.fab_add)
                sn.show()
                mAlertDialog.dismiss()
            }
        }
    }

    class ViewHolder(var binding: ItemMealScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listMealSchedule.size
    }

}