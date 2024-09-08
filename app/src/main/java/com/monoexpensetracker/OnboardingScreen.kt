package com.monoexpensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.monoexpensetracker.model.MoneyViewModel

class OnboardingScreen : AppCompatActivity() {

    private val moneyViewModel: MoneyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_screen)

        val button = findViewById<Button>(R.id.get_started_btn)
        var userName = findViewById<EditText>(R.id.username)

        // Check if username already exists
        val existingUsername = moneyViewModel.loadUserName()

        if (existingUsername != null) {
            // If username exists, skip onboarding and go to MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("username", existingUsername)
            }
            startActivity(intent)
            finish()
        } else {
            // Proceed with onboarding if no username is found
            setupOnboarding()
        }
    }

    private fun setupOnboarding() {
        val button = findViewById<Button>(R.id.get_started_btn)
        val userName = findViewById<EditText>(R.id.username)

        button.setOnClickListener {
            val nameText = userName.text.toString()

            // Check if user entered a name in EditText
            if (nameText.isNotEmpty()) {
                // Save the username from EditText into ViewModel
                moneyViewModel.saveUserName(nameText)

                // Proceed to MainActivity and pass the username
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("username", nameText)
                }
                startActivity(intent)
                finish() // Optional: finish onboarding screen
            } else {
                // Show error message if no name is entered
                Toast.makeText(applicationContext, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}