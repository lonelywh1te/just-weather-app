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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.lonelywh1te.justweather.R
import ru.lonelywh1te.justweather.databinding.FragmentSearchLocationBinding
import ru.lonelywh1te.justweather.domain.models.SearchLocation
import ru.lonelywh1te.justweather.presentation.adapter.SearchLocationAdapter
import ru.lonelywh1te.justweather.presentation.state.UIState
import ru.lonelywh1te.justweather.presentation.viewmodel.MainActivityViewModel
import ru.lonelywh1te.justweather.presentation.viewmodel.SearchLocationViewModel

class SearchLocationFragment : Fragment(), MenuProvider {
    private var _binding: FragmentSearchLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycler: RecyclerView
    private lateinit var rvAdapter: SearchLocationAdapter

    private val activityViewModel by activityViewModel<MainActivityViewModel>()
    private val viewModel by viewModel<SearchLocationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        _binding = FragmentSearchLocationBinding.inflate(inflater, container, false)

        rvAdapter = SearchLocationAdapter(onLocationClick = { location ->
            lifecycleScope.launch {
                viewModel.select(location)
                activityViewModel.getLastUserLocation()
                findNavController().popBackStack()
            }
        })

        recycler = binding.rvLocations.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = rvAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchState.collectLatest { state ->
                    updateUI(state)
                }
            }
        }

        return binding.root
    }

    private fun updateUI(state: UIState<List<SearchLocation>>) {
        when (state) {
            is UIState.Loading -> {
                binding.pbLoading.visibility = View.VISIBLE
            }
            is UIState.Success -> {
                binding.pbLoading.visibility = View.GONE
                rvAdapter.submit(state.data)
            }
            is UIState.Error -> {
                binding.pbLoading.visibility = View.GONE
                showError(state)
            }
            else -> return
        }
    }

    // TODO: show by view?
    private fun showError(state: UIState.Error<*>) {
        Snackbar
            .make(binding.root, state.exception?.message.toString(), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.search -> viewModel.search(activityViewModel.locationQuery.value)
            android.R.id.home -> findNavController().popBackStack()
        }

        return true
    }
}