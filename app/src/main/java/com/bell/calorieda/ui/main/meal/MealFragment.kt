package com.bell.calorieda.ui.main.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bell.calorieda.data.entity.Meal
import com.bell.calorieda.databinding.FragmentMealBinding
import com.bell.calorieda.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MealFragment : Fragment() {

    private lateinit var mealViewModel: MealViewModel
    private var _binding: FragmentMealBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var adapter : MealAdapter

        val factory = ViewModelFactory.getInstance(requireContext())
        mealViewModel =
            ViewModelProvider(this, factory)[MealViewModel::class.java]

        val mealSchedule = mealViewModel.getMealSchedule()
        val meal = mealViewModel.meal
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeNow = timeFormat.format(calendar.time)
        val dateNow = sdf.format(calendar.time)
        if(meal!=null){
            if (meal.date!=dateNow){
                if(mealSchedule.isNotEmpty()){
                    mealSchedule.map {
                        mealViewModel.insertMeal(
                            Meal(
                                meal = it.meal,
                                time = it.time,
                                status = "0",
                                date = dateNow
                            )
                        )
                    }
                }
            }
        }

        val mealDate = mealViewModel.getMealbyDate(dateNow)
        mealDate.map {
            if(it.time<timeNow){
                if(it.status=="0"){
                    mealViewModel.updateStatus("2",dateNow,it.meal)
                }
            }
        }

        mealViewModel.getMeal(dateNow).observe(viewLifecycleOwner,{
            adapter = MealAdapter(it)
            binding.rvMeal.layoutManager = LinearLayoutManager(context)
            binding.rvMeal.setHasFixedSize(true)
            binding.rvMeal.adapter = adapter
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}