package com.example.cuesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the transition from the system splash screen
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivSplashGif = findViewById<ImageView>(R.id.ivSplashGif)

        // Load the cloud_jump GIF using Glide
        // Code assisted by Google Gemini (Android Studio AI)
        try {
            Glide.with(this)
                .asGif()
                .load(R.drawable.cloud_jump)
                .into(ivSplashGif)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Navigate to MainActivity after 3 seconds (or match the GIF duration)
        // Code assisted by Google Gemini (Android Studio AI)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}