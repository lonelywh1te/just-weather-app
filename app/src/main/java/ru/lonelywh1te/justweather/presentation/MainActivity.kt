package ru.lonelywh1te.justweather.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.ActivityMainBinding
import ru.lonelywh1te.justweather.domain.models.Location
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel

private const val LOG_TAG = "MainActivity"

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = getViewModel()

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = null

            when (destination.id) {
                R.id.weatherFragment -> {
                    initFlowCollectors()

                    hideSearchField()
                    hideKeyboard()
                    showLocationButton()

                    binding.toolbarTitle.setOnClickListener {
                        navController.navigate(R.id.to_searchCityFragment)
                    }
                }

                R.id.searchCityFragment -> {
                    hideToolbarTitle()
                    showSearchField()
                    showKeyboard()
                }

                R.id.settingsFragment -> {
                    showToolbarTitle(destination.label.toString())
                    binding.toolbarTitle.setOnClickListener(null)
                }
            }
        }

        setContentView(binding.root)
    }

    private fun initFlowCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userLocation.collect { state ->
                        when (state) {
                            is UIState.Success -> {
                                showToolbarTitle(state.data.name)
                            }
                            is UIState.Error -> {
                                showToolbarTitle("Выбрать город")
                            }
                            is UIState.Loading -> {}
                            is UIState.Init -> {}
                        }
                    }
                }

                launch {
                    viewModel.foundLocation.collect { state ->
                        when (state) {
                            is UIState.Success -> {
                                showCheckLocationDialog(state.data)
                            }
                            is UIState.Error -> {
                                Snackbar.make(binding.root, "Не удалось определить ваше местоположение", Snackbar.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }

            }
        }
    }

    private fun showCheckLocationDialog(location: Location) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Вы сейчас находитесь в городе ${location.name}")
            .setMessage("Ваше местоположение определено верно?")
            .setPositiveButton("Да") { dialog, _ ->
                viewModel.selectUserLocation(location)
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                navController.navigate(R.id.to_searchCityFragment)
                dialog.dismiss()
            }

        dialog.show()
    }

    private fun showToolbarTitle(title: String) {
        binding.toolbarTitle.text = title
        if (binding.toolbarTitle.visibility == View.GONE) {
            binding.toolbarTitle.visibility = View.VISIBLE
        }
    }

    private fun hideToolbarTitle() {
        if (binding.toolbarTitle.visibility == View.VISIBLE) {
            binding.toolbarTitle.visibility = View.GONE
        }
    }

    private fun showSearchField() {
        binding.etSearchField.visibility = View.VISIBLE
        binding.etSearchField.setHint(binding.toolbarTitle.text)
        binding.etSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setLocationQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSearchField.requestFocus()
    }

    private fun hideSearchField() {
        if (binding.etSearchField.visibility == View.VISIBLE) {
            binding.etSearchField.setText("")
            binding.etSearchField.visibility = View.GONE
        }
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

