package com.bell.calorieda.ui.detailNutrient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bell.calorieda.R
import com.bell.calorieda.data.response.FoodsItem
import com.bell.calorieda.databinding.ActivityNutrientDetailBinding
import com.bell.calorieda.util.NutritionFactsData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NutrientDetailActivity : AppCompatActivity() {
    private var _binding: ActivityNutrientDetailBinding? = null
    private val binding get() = _binding
    private lateinit var nutrientDetailViewModel: NutrientDetailViewModel

    companion object {
        const val EXTRA_FOOD = "extra_food"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNutrientDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Calories"

        nutrientDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NutrientDetailViewModel::class.java]

        val foodName = intent.getStringExtra(EXTRA_FOOD)
        showLoading(true)
        nutrientDetailViewModel.setSearchFood(foodName)
        nutrientDetailViewModel.getFoods().observe(this, {
            if (it != null) {
                if (it.isNotEmpty()) {
                    showLoading(false)
                    binding?.imgDetail?.let { it1 ->
                        Glide.with(this)
                            .load(it[0].photo?.highres)
                            .apply(RequestOptions().override(90, 90))
                            .into(it1)
                    }
                    initContent(it)
                    populateNutritionFacts(it)
                } else {
                    Toast.makeText(this, "Tidak ada ", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initContent(food: ArrayList<FoodsItem>?) {
        food?.forEach { it1 ->
            binding?.apply {
                titleDetail.text = getString(R.string.detail_food, it1.foodName)
                tvCalories.text = getString(R.string.Calorie, it1.nfCalories)
                tvMeasure.text = getString(R.string.unit, it1.servingQty, it1.servingUnit)
                tvWeight.text = getString(R.string.weight, it1.servingWeightGrams)
            }
        }
    }

    private fun populateNutritionFacts(food: ArrayList<FoodsItem>?) {
        food?.forEach {
            val nfData = NutritionFactsData.Builder()
                .setServingSize(it.servingWeightGrams)
                .setCalories(it.nfCalories)
                .setTotalFat(it.nfTotalFat)
                .setSaturatedFat(it.nfSaturatedFat)
                .setCholesterol(it.nfCholesterol)
                .setSodium(it.nfSodium)
                .setTotalCarbohydrates(it.nfTotalCarbohydrate)
                .setDietaryFiber(it.nfDietaryFiber)
                .setSugars(it.nfSugars)
                .setProtein(it.nfProtein)
                .setPotassium(it.nfPotassium)
                .create()
            binding?.nfView?.addData(nfData)
        }
        binding?.nfView?.drawLabel()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.tvCalories?.visibility = View.GONE
            binding?.tvMeasure?.visibility = View.GONE
            binding?.tvWeight?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.tvCalories?.visibility = View.VISIBLE
            binding?.tvMeasure?.visibility = View.VISIBLE
            binding?.tvWeight?.visibility = View.VISIBLE
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