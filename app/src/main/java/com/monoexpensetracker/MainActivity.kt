package com.monoexpensetracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.monoexpensetracker.adapter.ExpenseAdapter
import com.monoexpensetracker.databinding.ActivityMainBinding
import com.monoexpensetracker.dataclass.Constant

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


        navController = findNavController(R.id.navHostFragment)
        bottomNavigationView =binding.bottomNavigationViews

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.statisticsFragment,R.id.walletFragment,R.id.profileFragment))
        bottomNavigationView.setupWithNavController(navController)
        //setupActionBarWithNavController(navController,appBarConfiguration)



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}