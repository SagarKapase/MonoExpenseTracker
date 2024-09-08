package com.monoexpensetracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.monoexpensetracker.model.MoneyViewModel

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var moneyViewModel: MoneyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        moneyViewModel = ViewModelProvider(this).get(MoneyViewModel::class.java)

        if (moneyViewModel.isFirstLaunch())
        {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this,OnboardingScreen::class.java)
                startActivity(intent)
                moneyViewModel.setFirstLaunchCompleted()
                finish()
            },3000)
        }else
        {
            Handler(Looper.getMainLooper()).postDelayed({
                val existingUsername = moneyViewModel.loadUserName()
                val intent = Intent(this,MainActivity::class.java).apply {
                    putExtra("username",existingUsername)
                }
                startActivity(intent)
                finish()
            },3000)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}