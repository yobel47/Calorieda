package com.bell.calorieda.ui.main.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bell.calorieda.databinding.FragmentReportBinding
import com.bell.calorieda.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        reportViewModel =
            ViewModelProvider(this,factory)[ReportViewModel::class.java]

        _binding = FragmentReportBinding.inflate(inflater, container, false)
        var mealAdapter : ReportAdapter


        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            reportViewModel.getMealbyDateStatus(sdf.format(calendar.time).toString()).observe(viewLifecycleOwner,{ meal ->
                reportViewModel.getDrinkbyDate(sdf.format(calendar.time).toString()).observe(viewLifecycleOwner,{ drink ->
                    reportViewModel.getCaloriesData(sdf.format(calendar.time).toString()).observe(viewLifecycleOwner,{ calories ->
                        if(drink.isNullOrEmpty()){
                            binding.rvReport.visibility = View.INVISIBLE
                            binding.tvReportEmpty.visibility = View.VISIBLE
                        }else{
                            mealAdapter = ReportAdapter(meal,drink,calories)
                            binding.tvReportEmpty.visibility = View.INVISIBLE
                            binding.rvReport.visibility = View.VISIBLE
                            binding.rvReport.layoutManager = LinearLayoutManager(context)
                            binding.rvReport.setHasFixedSize(true)
                            binding.rvReport.adapter = mealAdapter
                        }
                    })
                })
            })
        }

        val dateNow = sdf.format(calendar.time)
        reportViewModel.getMealbyDateStatus(dateNow).observe(viewLifecycleOwner,{ meal ->
            reportViewModel.getDrinkbyDate(dateNow).observe(viewLifecycleOwner,{ drink ->
                reportViewModel.getCaloriesData(dateNow).observe(viewLifecycleOwner, { calories ->
                    mealAdapter = ReportAdapter(meal, drink, calories)
                    binding.rvReport.layoutManager = LinearLayoutManager(context)
                    binding.rvReport.setHasFixedSize(true)
                    binding.rvReport.adapter = mealAdapter
                })
            })
        })


        reportViewModel.getData(dateNow)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}