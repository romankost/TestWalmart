package com.romakost.testwalmart.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romakost.testwalmart.data.CountryRepository
import com.romakost.testwalmart.data.CountryRepositoryImp
import com.romakost.testwalmart.data.CountryResponse
import com.romakost.testwalmart.data.GenericResult
import com.romakost.testwalmart.present.CountryPresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CountriesState(
    val countries: List<CountryPresentationModel>,
    val isLoading: Boolean,
    val showEmptyListMessage: Boolean,
    val error: String
) {
    val isErrorState = error.isNotEmpty()

    companion object {
        val initState = CountriesState(countries = emptyList(), isLoading = false, error = "", showEmptyListMessage = false)
    }
}

class CountriesVM(
    private val repository: CountryRepository = CountryRepositoryImp.instance
): ViewModel() {
    private val state = MutableStateFlow(CountriesState.initState)
    val countriesState = state.asStateFlow()

    init {
        fetchCountyList()
    }

    private fun updateState(reducer: (CountriesState) -> CountriesState) {
        state.value = reducer(state.value)
    }

    private fun fetchCountyList() {
        viewModelScope.launch {
            repository.fetchCounties()
                 .collect {
                    handleResponse(it)
                }
        }
    }

    private fun handleResponse(response: GenericResult<List<CountryResponse>>) {
        when (response) {
            is GenericResult.Progress -> updateState { it.copy(isLoading = true)}
            is GenericResult.Success -> {
                val countries = response.data.map { it.mapToPresentationModel() }
                updateState {
                    it.copy(
                        countries = countries,
                        isLoading = false,
                        showEmptyListMessage = countries.isEmpty()
                    )
                }
            }

            is GenericResult.Error -> updateState {
                it.copy(
                    isLoading = false,
                    error = response.errorMessage,
                    showEmptyListMessage = false
                )
            }
        }
    }

    private fun CountryResponse.mapToPresentationModel(): CountryPresentationModel {
        val nameAndRegion = when {
            name.isEmpty() -> region
            region.isEmpty() -> name
            else -> "$name, $region"
        }

        return CountryPresentationModel(
            nameAndRegion = nameAndRegion,
            capital = capital,
            code = code
        )
    }
}