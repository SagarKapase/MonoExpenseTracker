package com.monoexpensetracker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.monoexpensetracker.adapter.ExpenseAdapter
import com.monoexpensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView : BottomNavigationView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


       /* navController = findNavController(R.id.navHostFragment)
        bottomNavigationView =binding.bottomNavigationViews

        *//*val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.statisticsFragment,R.id.walletFragment,R.id.profileFragment))
        bottomNavigationView.setupWithNavController(navController)*//*

        bottomNavigationView.setupWithNavController(navController)*/
        // Set up the NavController with BottomNavigationView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViews)
        bottomNavigationView.setupWithNavController(navController)


        val username = intent.getStringExtra("username")
        if (username != null)
        {
            val bundle = Bundle().apply {
                putString("username",username)
            }
            // Navigate to HomeFragment using the NavController and pass the bundle
            navController.navigate(R.id.homeFragment, bundle)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}