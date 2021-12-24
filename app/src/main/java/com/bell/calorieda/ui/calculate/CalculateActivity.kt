package com.bell.calorieda.ui.calculate

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bell.calorieda.R
import com.bell.calorieda.databinding.ActivityCalculateBinding
import com.bell.calorieda.ui.nutrient.NutrientActivity

class CalculateActivity : AppCompatActivity() {
    private var _binding : ActivityCalculateBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate Calories"
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding?.btnCalculate?.setOnClickListener {
            var search = binding?.edQuery?.text.toString()
            val intent = Intent(this, NutrientActivity::class.java)
            if (search.isNotEmpty()){
                intent.putExtra(NutrientActivity.EXTRA_QUERY,search)
                startActivity(intent)
            } else {
                search = getString(R.string.autofill_query)
                intent.putExtra(NutrientActivity.EXTRA_QUERY,search)
                startActivity(intent)
            }
        }
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) finish() else super.onBackPressed()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}