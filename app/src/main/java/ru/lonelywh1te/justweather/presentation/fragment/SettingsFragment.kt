package ru.lonelywh1te.justweather.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentSettingsBinding
import ru.lonelywh1te.justweather.domain.enums.TemperatureUnit
import ru.lonelywh1te.justweather.domain.enums.WindSpeedUnit
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.SettingsViewModel

private const val LOG_TAG = "SettingsFragment"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()
    private val activityViewModel: MainActivityViewModel by activityViewModel<MainActivityViewModel>()

    private lateinit var arrayOfTempUnits: Array<String>
    private lateinit var arrayOfWindSpeedUnit: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayOfTempUnits = arrayOf(requireContext().getString(R.string.c_unit), requireContext().getString(R.string.f_unit))
        arrayOfWindSpeedUnit = arrayOf(requireContext().getString(R.string.km_h), requireContext().getString(R.string.mph))

        initFlowCollectors()

        binding.tvTempUnitParam.setOnClickListener {
            showSingleChoiceDialog(getString(R.string.select_a_unit_of_measurement), arrayOfTempUnits, viewModel.temperatureUnit.value.code) { code ->
                viewModel.changeTemperatureUnit(TemperatureUnit.fromCode(code))
                activityViewModel.updateTemperatureUnit()
            }
        }

        binding.tvWindSpeedParam.setOnClickListener {
            showSingleChoiceDialog(getString(R.string.select_a_unit_of_measurement), arrayOfWindSpeedUnit, viewModel.windSpeedUnit.value.code) { code ->
                viewModel.changeWindSpeedUnit(WindSpeedUnit.fromCode(code))
                activityViewModel.updateWindSpeedUnit()
            }
        }
    }

    private fun initFlowCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.temperatureUnit.collect { unit ->
                        binding.tvTempUnitParam.text = arrayOfTempUnits[unit.code]
                    }
                }

                launch {
                    viewModel.windSpeedUnit.collect { unit ->
                        binding.tvWindSpeedParam.text = arrayOfWindSpeedUnit[unit.code]
                    }
                }
            }
        }
    }

    private fun showSingleChoiceDialog(
        title: String,
        items: Array<String>,
        defaultCheckedItem: Int = 0,
        onChangeParamListener: (checkedIndex: Int) -> Unit
    ) {
        var checkedItem = defaultCheckedItem

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setSingleChoiceItems(items, checkedItem) { _, checked ->
                checkedItem = checked
            }
            .setPositiveButton(requireContext().getString(R.string.select)) { d, _ ->
                onChangeParamListener.invoke(checkedItem)
                d.dismiss()

            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { d, _ ->
                d.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}