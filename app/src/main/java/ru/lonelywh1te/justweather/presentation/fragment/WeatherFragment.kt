package ru.lonelywh1te.justweather.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentWeatherBinding
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel


class WeatherFragment : Fragment(), MenuProvider {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<WeatherFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getCurrentWeatherInfo("Владивосток")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentWeatherState.collectLatest { state ->
                    changeState(state)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeState(state: UIState<WeatherInfo>) {
        when (state) {
            is UIState.Loading -> {} // TODO: Show loading
            is UIState.Success -> updateUI(state.data)
            is UIState.Error -> {} // TODO: Show error
            is UIState.Init -> {} // TODO: nothing
        }
    }

    private fun updateUI(weatherInfo: WeatherInfo) {
        binding.tvLastUpdatedValue.text = weatherInfo.current.lastUpdated.toString()
        binding.tvCondition.text = weatherInfo.current.condition.text
        binding.tvCurrentTemp.text = weatherInfo.current.tempC.toString()
        binding.tvWindSpeedValue.text = weatherInfo.current.windKph.toString()
        binding.tvUvValue.text = weatherInfo.current.uv.toString()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.settings -> findNavController().navigate(R.id.to_settingsFragment)
            android.R.id.home -> TODO("Location button not yet implemented")
        }

        return true
    }
}