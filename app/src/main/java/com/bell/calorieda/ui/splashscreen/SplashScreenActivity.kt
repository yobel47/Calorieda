package com.bell.calorieda.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.bell.calorieda.R
import com.bell.calorieda.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottie : LottieAnimationView = findViewById(R.id.lottie)

        lottie.animate().translationY(-1800f).translationX(1800f).setDuration(1000).startDelay = 3500

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        },3500L)

        supportActionBar?.hide()
    }
}