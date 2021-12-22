package com.bell.calorieda.ui.main.calories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bell.calorieda.data.entity.Calories
import com.bell.calorieda.databinding.FragmentCaloriesBinding
import com.bell.calorieda.databinding.FragmentDrinkBinding
import com.bell.calorieda.ui.calculate.CalculateActivity
import com.bell.calorieda.ui.main.drink.DrinkViewModel
import com.bell.calorieda.ui.setting.SettingsActivity
import com.bell.calorieda.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class CaloriesFragment : Fragment() {

    private lateinit var caloriesViewModel: CaloriesViewModel
    private var _binding: FragmentCaloriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaloriesBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        caloriesViewModel =
            ViewModelProvider(this, factory)[CaloriesViewModel::class.java]

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault())
        val sdf1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateNow = sdf.format(calendar.time)
        val dateNow1 = sdf1.format(calendar.time)
        binding.tvDate.text = dateNow

        binding.btnDrink.setOnClickListener {
            val intent = Intent(context, CalculateActivity::class.java)
            startActivity(intent)
        }

        val calories = caloriesViewModel.caloriesData
        if(calories.date!=dateNow1){
            caloriesViewModel.insert(Calories(kcal = "0", date = dateNow1))
        }

        caloriesViewModel.caloriesLiveData.observe(viewLifecycleOwner,{
            binding.tvKcalValue.text = it.kcal
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}