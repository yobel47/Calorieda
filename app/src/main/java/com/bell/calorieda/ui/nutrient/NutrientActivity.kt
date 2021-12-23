package com.bell.calorieda.ui.nutrient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bell.calorieda.R
import com.bell.calorieda.data.response.FoodsItem
import com.bell.calorieda.databinding.ActivityNutrientBinding
import com.bell.calorieda.util.ViewModelFactory
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NutrientActivity : AppCompatActivity() {
    private var _binding: ActivityNutrientBinding? = null
    private val binding get() = _binding
    private lateinit var nutrientViewModel: NutrientViewModel
    private var foods: ArrayList<FoodsItem>? = null

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNutrientBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nutritional Calories"
        val factory = ViewModelFactory.getInstance(applicationContext)
        nutrientViewModel = ViewModelProvider(
            this,
            factory
        )[NutrientViewModel::class.java]

        initRecyclerList()

        val query = intent.getStringExtra(EXTRA_QUERY)

        showLoading(true)
        nutrientViewModel.setSearchFood(query)
        nutrientViewModel.getFoods().observe(this, {
            Log.d(this@NutrientActivity::class.java.simpleName, "this is $it")
            if (it != null) {
                if (it.size > 0) {
                    showLoading(false)
                    foods = it
                    showRecyclerView(it)
                    calculate()
                    binding?.emptySearch?.linearResult?.visibility = View.GONE
                }
            } else {
                showLoading(false)
                showEmptyResult()
            }
        })
    }

    private fun calculate() {
        var totalCalories = 0.0f
        var totalCarbohydrates = 0.0f
        var totalFats = 0.0f
        var totalProteins = 0.0f

        foods?.let { foods ->
            foods.forEach {
                totalCalories += it.nfCalories?.toFloat()!!
                totalCarbohydrates += it.nfTotalCarbohydrate?.toFloat()!!
                totalFats += it.nfTotalFat?.toFloat()!!
                totalProteins += it.nfProtein?.toFloat()!!
            }
        }

        val caloriesFromCarbohydrates = totalCarbohydrates * 4
        val caloriesFromFats = totalFats * 9
        val caloriesFromProteins = totalProteins * 4

        val chartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Source of Calories")
            .subtitle(getString(R.string.total_calories, totalCalories))
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#61AAEE", "#EBEE61", "#64EE61"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .dataLabels(AADataLabels().format("<b>{point.name}</b>: {point.percentage:.1f}%"))
                        .name("Total calories (kcal)")
                        .data(
                            arrayOf(
                                arrayOf("Carbohydrates", caloriesFromCarbohydrates),
                                arrayOf("Fats", caloriesFromFats),
                                arrayOf("Proteins", caloriesFromProteins),
                            )
                        )
                )
            )
        binding?.chartCalories?.aa_drawChartWithChartModel(chartModel)
        binding?.btnAdd?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateNow = sdf.format(calendar.time)
            val calories = nutrientViewModel.getCaloriesByDate(dateNow)
            val oldKcal = calories.kcal
            val newKcal = oldKcal.toFloat()+totalCalories
            nutrientViewModel.updateCalories(newKcal.toString(),dateNow)
            Snackbar.make(it,"Calories data has been added", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showEmptyResult() {
        binding?.emptySearch?.linearResult?.visibility = View.VISIBLE
        binding?.emptySearch?.ufo?.animate()?.setDuration(3000L)?.startDelay = 2500
        binding?.btnAdd?.visibility = View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.btnAdd?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.btnAdd?.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerList() {
        binding?.rvResult?.layoutManager = LinearLayoutManager(this)
        binding?.rvResult?.setHasFixedSize(true)
        binding?.rvResult?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun showRecyclerView(listfood: ArrayList<FoodsItem>) {
        val listFoodAdapter = NutrientAdapter(listfood)
        binding?.rvResult?.adapter = listFoodAdapter
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