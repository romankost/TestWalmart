package com.romakost.testwalmart.present

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romakost.testwalmart.R
import com.romakost.testwalmart.databinding.ActivityMainBinding
import com.romakost.testwalmart.domain.CountriesState
import com.romakost.testwalmart.domain.CountriesVM
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: CountriesVM by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            rvCountries.adapter = CountriesAdapter()
            rvCountries.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager(this@MainActivity).orientation
                )
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countriesState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: CountriesState) {
        when {
            state.isErrorState -> renderError(state.error)
            state.isLoading -> renderProgress()
            else -> renderCountryList(countries = state.countries, showEmptyListMessage = state.showEmptyListMessage)
        }
    }

    private fun renderError(message: String) {
        with(binding) {
            tvErrorMessage.text = message
            tvErrorMessage.isVisible = true
            pbCountriesLoading.isVisible = false
            rvCountries.isVisible = false
            tvNoItems.isVisible = false
        }
    }

    private fun renderProgress() {
        with(binding) {
            tvErrorMessage.isVisible = false
            pbCountriesLoading.isVisible = true
            rvCountries.isVisible = false
            tvNoItems.isVisible = false
        }
    }

    private fun renderCountryList(countries: List<CountryPresentationModel>, showEmptyListMessage: Boolean) {
        with(binding) {
            tvErrorMessage.isVisible = false
            pbCountriesLoading.isVisible = false
            tvNoItems.isVisible = showEmptyListMessage
            rvCountries.isVisible = true
            (rvCountries.adapter as CountriesAdapter).updateCountriesList(countries)
        }
    }
}