package ru.lonelywh1te.justweather.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentWeatherBinding
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit
import ru.lonelywh1te.justweather.domain.models.WeatherInfo
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.utils.UiUtils
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.WeatherFragmentViewModel

private const val LOG_TAG = "WeatherFragment"
private const val PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

class WeatherFragment : Fragment(), MenuProvider {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val activityViewModel by activityViewModel<MainActivityViewModel>()
    private val viewModel by viewModel<WeatherFragmentViewModel>()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                findUserLocation()
            } else {
                showError(UIState.Error<Nothing>(null, Exception("Для определения местоположения необходимо разрешение")))
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

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
                        firstLaunchView(false)
                    }
                }

                launch {
                    activityViewModel.userLocation.collect { state ->
                        when (state) {
                            is UIState.Success -> {
                                viewModel.getForecastWeatherInfo(state.data.name)
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

        binding.tvThirdDayName.text = UiUtils.getThirdDayName()
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
        binding.tvCondition.text = weatherInfo.current.condition.text.lowercase()
        binding.tvUvValue.text = weatherInfo.current.uv.toString()

        when(activityViewModel.windSpeedUnit.value) {
            WindSpeedUnit.KPH -> {
                binding.tvWindSpeedParam.text = requireContext().getString(R.string.km_h)
                binding.tvWindSpeedValue.text = weatherInfo.current.windKph.toString()
            }
            WindSpeedUnit.MPH -> {
                binding.tvWindSpeedParam.text = requireContext().getString(R.string.mph)
                binding.tvWindSpeedValue.text = weatherInfo.current.windMph.toString()
            }
        }

        weatherInfo.forecast?.let { forecast ->
            val forecastDaysValuesList = listOf(
                binding.tvTodayMinMaxTemp,
                binding.tvTomorrowMinMaxTemp,
                binding.tvThirdMinMaxTemp,
            )

            when (activityViewModel.temperatureUnit.value) {
                TemperatureUnit.C -> {
                    binding.tvTempUnit.text = requireContext().getString(R.string.c_unit)
                    binding.tvCurrentTemp.text = weatherInfo.current.tempC.toString()
                    binding.tvMinTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.minTempC)
                    binding.tvMaxTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.maxTempC)

                    forecastDaysValuesList.forEachIndexed { index, textView ->
                        textView.text = UiUtils.toMinMaxPattern(forecast.forecastDays[index].day.minTempC, forecast.forecastDays[index].day.maxTempC)
                    }
                }
                TemperatureUnit.F -> {
                    binding.tvTempUnit.text = requireContext().getString(R.string.f_unit)
                    binding.tvCurrentTemp.text = weatherInfo.current.tempF.toString()
                    binding.tvMinTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.minTempF)
                    binding.tvMaxTemp.text = UiUtils.toTempPattern(forecast.forecastDays[0].day.maxTempF)

                    forecastDaysValuesList.forEachIndexed { index, textView ->
                        textView.text = UiUtils.toMinMaxPattern(forecast.forecastDays[index].day.minTempF, forecast.forecastDays[index].day.maxTempF)
                    }
                }
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


    private fun findUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    activityViewModel.findUserLocation(it.latitude, it.longitude)
                }
            }
            .addOnFailureListener {
                showError(UIState.Error<Nothing>(null, Exception(getString(R.string.failed_determine_location))))
            }
    }


    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), PERMISSION_ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            findUserLocation()
        } else {
            requestPermissionLauncher.launch(PERMISSION_ACCESS_COARSE_LOCATION)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.settings -> findNavController().navigate(R.id.to_settingsFragment)
            android.R.id.home -> findUserLocation()
        }

        return true
    }
}