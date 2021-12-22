package com.bell.calorieda.ui.drinkSchedule

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.DrinkSchedule
import com.bell.calorieda.databinding.ItemDrinkScheduleBinding
import com.bell.calorieda.util.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class DrinkScheduleAdapter(private val listDrinkSchedule: List<DrinkSchedule>) :
    RecyclerView.Adapter<DrinkScheduleAdapter.ViewHolder>() {
    private lateinit var drinkScheduleViewModel: DrinkScheduleViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDrinkScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, time) = listDrinkSchedule[position]
        holder.binding.tvDrinkSchedule.text = time
        holder.itemView.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(it.context).inflate(R.layout.drink_schedule_dialog, null)
            val mBuilder = AlertDialog.Builder(it.context, R.style.CustomAlertDialog)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mDialogView.findViewById<ImageButton>(R.id.btn_close).setOnClickListener {
                mAlertDialog.dismiss()
            }
            val btnScheduleTime = mDialogView.findViewById<ImageButton>(R.id.btn_time_picker)
            val tvScheduleTime = mDialogView.findViewById<TextView>(R.id.tv_time_schedule)
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
            drinkScheduleViewModel =
                ViewModelProvider(
                    it.findViewTreeViewModelStoreOwner()!!,
                    factory
                )[DrinkScheduleViewModel::class.java]
            btnUpdateSchedule.setOnClickListener {
                drinkScheduleViewModel.updateDrinkSchedule(
                    id = id,
                    time = tvScheduleTime.text.toString()
                )
                val sn = Snackbar.make(holder.itemView,"Drink schedule updated", Snackbar.LENGTH_SHORT)
                sn.anchorView = holder.itemView.rootView.findViewById(R.id.fab_add)
                sn.show()
                mAlertDialog.dismiss()
            }
            btnDeleteSchedule.setOnClickListener {
                drinkScheduleViewModel.delete(id)
                val sn = Snackbar.make(holder.itemView,"Drink schedule deleted", Snackbar.LENGTH_SHORT)
                sn.anchorView = holder.itemView.rootView.findViewById(R.id.fab_add)
                sn.show()
                mAlertDialog.dismiss()
            }
        }
    }

    class ViewHolder(var binding: ItemDrinkScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listDrinkSchedule.size
    }


}