package com.bell.calorieda.ui.nutrient

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bell.calorieda.R
import com.bell.calorieda.data.response.FoodsItem
import com.bell.calorieda.databinding.ItemFoodsBinding
import com.bell.calorieda.ui.detailNutrient.NutrientDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NutrientAdapter(private val listFood: ArrayList<FoodsItem>) : RecyclerView.Adapter<NutrientAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFoodsBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        with(viewHolder){
            with(listFood[position]) {
                binding.apply {
                    tvNameFood.text = foodName
                    tvCalorie.text = itemView.context.resources.getString(R.string.Calorie,nfCalories)
                    tvUnit.text = itemView.context.resources.getString(R.string.unit,servingQty,servingUnit)
                    tvWeight.text = itemView.context.resources.getString(R.string.weight,servingWeightGrams)
                    if (photo != null) {
                        Glide.with(itemView)
                            .load(photo.thumb)
                            .apply(RequestOptions().override(90, 90))
                            .into(imgItemAvatar)
                    }
                    itemView.setOnClickListener{
                        val intent = Intent(it.context, NutrientDetailActivity::class.java)
                        intent.putExtra(NutrientDetailActivity.EXTRA_FOOD, foodName)
                        it.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount() = listFood.size

    class ListViewHolder(val binding: ItemFoodsBinding)
        : RecyclerView.ViewHolder(binding.root)

}