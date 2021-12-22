package com.bell.calorieda.ui.main.drink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bell.calorieda.R
import com.bell.calorieda.data.entity.Drink
import com.bell.calorieda.databinding.FragmentDrinkBinding
import com.bell.calorieda.util.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class DrinkFragment : Fragment() {

    private lateinit var drinkViewModel: DrinkViewModel
    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        drinkViewModel =
            ViewModelProvider(this, factory)[DrinkViewModel::class.java]

        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val slider = binding.slider
        val sliderLabel = binding.sliderLabel
        var label = 50

        sliderLabel.text = resources.getString(R.string._50ml)
        slider.addOnChangeListener { _, value, _ ->
            label = value.toInt()
            sliderLabel.text = resources.getString(R.string.slider_label, label.toString())
        }

        val tvMl = binding.tvMl
        val tvGoal = binding.tvGoal
        val tvProgress = binding.tvPercentage

        var oldMl: String

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateNow = sdf.format(calendar.time)
        val id: Int

        val dd = drinkViewModel.drink
        if (dd.date != dateNow) {
            drinkViewModel.insert(
                Drink(
                    ml = "0",
                    progress = "0",
                    target = dd.target,
                    date = dateNow
                )
            )
            oldMl = "0"
            tvMl.text = "0"
            tvGoal.text = resources.getString(R.string.goal_1000ml)
            tvProgress.text = resources.getString(R.string._0_completed)
            id = dd.id + 1
        } else {
            oldMl = dd.ml
            tvMl.text = dd.ml
            tvGoal.text = resources.getString(R.string.new_goal,dd.target)
            tvProgress.text = resources.getString(R.string.new_progress,dd.progress)
            id = dd.id
        }


        binding.btnDrink.setOnClickListener {
            var newMl = oldMl.toInt() + label
            var newProgress = (newMl.toFloat() / dd.target.toFloat()) * 100
            if (newMl >= dd.target.toInt()) {
                newMl = dd.target.toInt()
            }
            if (newProgress >= 100) {
                newProgress = 100f
            }
            drinkViewModel.updateDrink(id, newMl.toString(), newProgress.toString())
            view?.let { it1 -> Snackbar.make(it1,"Drink progress has been updated", Snackbar.LENGTH_SHORT).show() }
        }

        drinkViewModel.drinkData.observe(viewLifecycleOwner, {
            oldMl = it.ml
            tvMl.text = it.ml
            tvGoal.text = resources.getString(R.string.new_goal,it.target)
            tvProgress.text = resources.getString(R.string.new_progress,it.progress)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}