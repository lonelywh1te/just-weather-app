package ru.lonelywh1te.justweather

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import ru.lonelywh1te.justweather.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = destination.label
            binding.toolbar.title = null

            when (destination.id) {
                R.id.weatherFragment -> {
                    showToolbarTitle()
                    showLocationButton()

                    binding.toolbarTitle.setOnClickListener {
                        navController.navigate(R.id.to_searchCityFragment)
                    }
                }

                R.id.searchCityFragment -> {
                    showSearchField()
                }

                else -> {
                    binding.toolbarTitle.setOnClickListener(null)
                }
            }
        }

        setContentView(binding.root)
    }

    private fun showToolbarTitle() {
        binding.toolbarTitle.visibility = View.VISIBLE
        binding.etSearchField.visibility = View.GONE

        hideKeyboard()
    }

    private fun showSearchField() {
        binding.toolbarTitle.visibility = View.GONE

        binding.etSearchField.visibility = View.VISIBLE
        binding.etSearchField.setOnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> TODO("Not yet implemented")
                else -> false
            }
        }
        binding.etSearchField.requestFocus()
        showKeyboard()
    }

    private fun showKeyboard() {
        inputMethodManager.showSoftInput(binding.etSearchField, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(binding.etSearchField.windowToken, 0)
    }

    private fun showLocationButton() {
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_location)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

