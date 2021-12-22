package com.bell.calorieda.ui.main.meal

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.databinding.ItemMealBinding
import com.bell.calorieda.ui.mealCheck.MealCheckActivity

class MealAdapter(private val listMeal: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, meal, time, status) = listMeal[position]
        holder.binding.tvMealName.text = meal
        holder.binding.tvMealTime.text = time
        if (status.toInt() != 0) {
            holder.binding.tvMealName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.tvMealTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.mealInfo.background =
                ColorDrawable(ContextCompat.getColor(holder.itemView.context, R.color.green))

            holder.binding.mealInfo.foreground =
                ColorDrawable(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MealCheckActivity::class.java)
            intent.putExtra(MealCheckActivity.EXTRA_NAME, meal)
            intent.putExtra(MealCheckActivity.EXTRA_TIME, time)
            intent.putExtra(MealCheckActivity.EXTRA_STATUS, status)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(var binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listMeal.size
    }
}