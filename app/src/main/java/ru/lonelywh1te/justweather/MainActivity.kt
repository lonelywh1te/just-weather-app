package ru.lonelywh1te.justweather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import ru.lonelywh1te.justweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.weatherFragment -> {
                    setupLocationButton(supportActionBar)
                }
            }
        }

        binding.toolbar.setOnClickListener {
            Toast.makeText(this, "Toolbar clicked", Toast.LENGTH_SHORT).show()
        }

        setContentView(binding.root)
    }
}

private fun setupLocationButton(actionBar: ActionBar?) {
    if (actionBar == null) return

    actionBar.setHomeAsUpIndicator(R.drawable.ic_location)
    actionBar.setDisplayHomeAsUpEnabled(true)
}