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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentWeatherBinding
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.utils.UiUtils
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel

private const val LOG_TAG = "WeatherFragment"

class WeatherFragment : Fragment(), MenuProvider {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val activityViewModel by activityViewModel<MainActivityViewModel>()
    private val viewModel by viewModel<WeatherFragmentViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFlowCollectors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFlowCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.currentWeatherState.collect { state ->
                        updateUI(state)
                    }
                }

                launch {
                    activityViewModel.userLocation.collect { state ->
                        when (state) {
                            is UIState.Success -> {
                                viewModel.getForecastWeatherInfo(state.data.name)
                                firstLaunchView(false)
                            }
                            is UIState.Error -> {
                                firstLaunchView(true)
                            }
                            else -> throw Exception("Unknown state")
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(state: UIState<WeatherInfo>) {
        when (state) {
            is UIState.Loading -> {
                updateProgressBarState(true)
                state.data?.let { updateWeatherInfo(it) }
            }
            is UIState.Success -> {
                updateProgressBarState(false)
                updateWeatherInfo(state.data)
            }

            is UIState.Error -> {
                updateProgressBarState(false)
                showError(state)
            }
            else -> return
        }
    }

    private fun firstLaunchView(isFirstLaunch: Boolean) {
        if (isFirstLaunch) {
            binding.tvHelloText.visibility = View.VISIBLE

            binding.addMetricsLayout.visibility = View.GONE
            binding.mainMetricsLayout.visibility = View.GONE
            binding.tvThreeDaysForecastTitle.visibility = View.GONE
            binding.threeDaysForecastLayout.visibility = View.GONE
            binding.lastUpdateLayout.visibility = View.GONE
        } else {
            binding.tvHelloText.visibility = View.GONE

            binding.addMetricsLayout.visibility = View.VISIBLE
            binding.mainMetricsLayout.visibility = View.VISIBLE
            binding.tvThreeDaysForecastTitle.visibility = View.VISIBLE
            binding.threeDaysForecastLayout.visibility = View.VISIBLE
            binding.lastUpdateLayout.visibility = View.VISIBLE
        }
    }

    private fun updateWeatherInfo(weatherInfo: WeatherInfo) {
        binding.tvLastUpdatedValue.text = UiUtils.dateFormat(weatherInfo.current.lastUpdated, "dd.MM.yyyy HH:mm")
        binding.tvCondition.text = weatherInfo.current.condition.text
        binding.tvCurrentTemp.text = weatherInfo.current.tempC.toString()
        binding.tvWindSpeedValue.text = weatherInfo.current.windKph.toString()
        binding.tvUvValue.text = weatherInfo.current.uv.toString()
        binding.tvThirdDayName.text = UiUtils.getThirdDayName()

        weatherInfo.forecast?.let { forecast ->
            binding.tvMinTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.minTempC)
            binding.tvMaxTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.maxTempC)

            val forecastDaysValuesList = listOf(
                binding.tvTodayMinMaxTemp,
                binding.tvTomorrowMinMaxTemp,
                binding.tvThirdMinMaxTemp,
            )

            forecastDaysValuesList.forEachIndexed { index, textView ->
                textView.text = UiUtils.toMinMaxPattern(forecast.forecastDays[index].day.minTempC, forecast.forecastDays[index].day.maxTempC)
            }
        }
    }

    private fun updateProgressBarState(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // TODO: show by view?
    private fun showError(state: UIState.Error<*>) {
        Snackbar
            .make(binding.root, state.exception?.message.toString(), Snackbar.LENGTH_LONG)
            .show()
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