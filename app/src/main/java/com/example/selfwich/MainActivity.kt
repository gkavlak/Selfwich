package com.example.selfwich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.selfwich.R.id
import com.example.selfwich.R.id.my_navFragment
import com.example.selfwich.R.id.my_nav_host
import com.example.selfwich.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        drawerLayout=findViewById(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)

        val navController = findNavController(my_navFragment)

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navView,navController)




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

