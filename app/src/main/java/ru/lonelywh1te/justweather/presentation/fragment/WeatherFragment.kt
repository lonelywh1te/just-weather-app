package ru.lonelywh1te.justweather.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentWeatherBinding


class WeatherFragment : Fragment(), MenuProvider {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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