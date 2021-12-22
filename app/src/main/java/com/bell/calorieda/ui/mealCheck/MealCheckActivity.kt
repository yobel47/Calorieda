package com.bell.calorieda.ui.mealCheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModelProvider
import com.bell.calorieda.databinding.ActivityMealCheckBinding
import com.bell.calorieda.ui.main.MainActivity
import com.bell.calorieda.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MealCheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealCheckBinding
    private lateinit var mealCheckViewModel: MealCheckViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meal: String = intent.getStringExtra(EXTRA_NAME).toString()
        val time: String = intent.getStringExtra(EXTRA_TIME).toString()
        val status: String = intent.getStringExtra(EXTRA_STATUS).toString()

        binding.tvName.text = meal
        binding.tvTime.text = time

        val factory = ViewModelFactory.getInstance(applicationContext)
        mealCheckViewModel =
            ViewModelProvider(this, factory)[MealCheckViewModel::class.java]

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateNow = sdf.format(calendar.time)

        if(status=="1"){
            val params = binding.fabSkip.layoutParams as RelativeLayout.LayoutParams
            params.setMargins(
                0,0,0,binding.fabSkip.marginBottom
            )
            params.addRule(RelativeLayout.ALIGN_PARENT_END,0)
            params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

            binding.fabSkip.layoutParams = params

            binding.fabEat.visibility = View.GONE
        }else if(status=="2"){
            val params = binding.fabEat.layoutParams as RelativeLayout.LayoutParams
            params.setMargins(
                0,0,0,binding.fabEat.marginBottom
            )
            params.addRule(RelativeLayout.ALIGN_PARENT_START,0)
            params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

            binding.fabEat.layoutParams = params

            binding.fabSkip.visibility = View.GONE
        }

        binding.fabEat.setOnClickListener{
            mealCheckViewModel.updateStatus("1",dateNow,meal)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.fabSkip.setOnClickListener{
            mealCheckViewModel.updateStatus("2",dateNow,meal)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.fabEat
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_STATUS = "extra_status"
    }
}