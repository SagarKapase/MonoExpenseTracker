package com.monoexpensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OnboardingScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_screen)

        val button = findViewById<Button>(R.id.get_started_btn)
        var userName = findViewById<EditText>(R.id.username)

        button.setOnClickListener()
        {
            val name = userName.text.toString()

            if(name == "" || name == null)
            {
                Toast.makeText(applicationContext,"Please enter your name",Toast.LENGTH_SHORT).show()
            }else
            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            /*startActivity(Intent(this,MainActivity::class.java))
            finish()*/
        }
    }
}