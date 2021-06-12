package com.example.selfwich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.selfwich.R.id
import com.example.selfwich.R.id.my_navFragment
import com.example.selfwich.R.id.my_nav_host
import com.google.android.material.bottomnavigation.BottomNavigationView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navController = findNavController(my_navFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(id.bottom_navbar)

        findViewById<BottomNavigationView>(id.bottom_navbar)
            .setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                id.loginFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                id.registerFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                id.drinksFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                id.eatsFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                id.selfwichFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

    }
}

