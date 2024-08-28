package com.monoexpensetracker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.findNavController
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


        navController = findNavController(R.id.navHostFragment)
        bottomNavigationView =binding.bottomNavigationViews

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.statisticsFragment,R.id.walletFragment,R.id.profileFragment))
        bottomNavigationView.setupWithNavController(navController)
        //setupActionBarWithNavController(navController,appBarConfiguration)

        /*bottomNavigationView.setOnNavigationItemSelectedListener {item ->
            bottomNavigationView.menu.forEach { menuItem ->
                val view = bottomNavigationView.findViewById<View>(menuItem.itemId)
                view?.setBackgroundColor(ContextCompat.getColor(this,android.R.color.transparent))
            }
            // Set background color for selected item
            val selectedView = bottomNavigationView.findViewById<View>(item.itemId)
            selectedView?.setBackgroundColor(
                ContextCompat.getColor(this, R.color.darkMainColor)
            )

            // Navigate to the selected fragment
            navController.navigate(item.itemId)
            true
        }*/

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}