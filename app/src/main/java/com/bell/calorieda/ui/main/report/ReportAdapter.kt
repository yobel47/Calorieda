package com.bell.calorieda.ui.main.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.Calories
import com.bell.calorieda.data.entity.Drink
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.databinding.ItemReportBinding

class ReportAdapter(
    private val listMeal: List<Meal>,
    private val listDrink: List<Drink>,
    private val listCalories: List<Calories>
) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            val (_, _, progress) = listDrink[0]
            holder.binding.tvReportName.text = holder.itemView.context.getString(R.string.drink)
            holder.binding.tvReportInfo.text =
                holder.itemView.context.getString(R.string.progress, progress)
        }else if(position == 1) {
            val (_, kcal, _) = listCalories[(position - listDrink.size)]
            holder.binding.tvReportName.text = holder.itemView.context.getString(R.string.calories)
            holder.binding.tvReportInfo.text = holder.itemView.context.getString(R.string.adapter_kcal, kcal)
        }else{
            val (_, meal, _, status) = listMeal[(position - listDrink.size - listCalories.size)]
            holder.binding.tvReportName.text = meal
            if (status == "1") {
                holder.binding.tvReportInfo.text = holder.itemView.context.getString(R.string.eaten)
            } else if (status == "2") {
                holder.binding.tvReportInfo.text =
                    holder.itemView.context.getString(R.string.skipped)
            }
        }
    }

    class ViewHolder(var binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listMeal.size + listDrink.size + listCalories.size
    }
}