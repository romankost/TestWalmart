package com.romakost.testwalmart.domain

import app.cash.turbine.test
import com.romakost.testwalmart.data.CountryRepository
import com.romakost.testwalmart.data.CountryResponse
import com.romakost.testwalmart.data.GenericResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesVMTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: CountryRepository
    private lateinit var viewModel: CountriesVM

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCountyList emits loading then success`() = runTest {
        // given
        val fakeResponse = listOf(CountryResponse("Ukraine", "UA", "Kiev", code = "UA"))
        whenever(repository.fetchCounties()).thenReturn(
            flow {
                emit(GenericResult.Success(fakeResponse))
            }
        )

        // when
        viewModel = CountriesVM(repository)

        // then
        viewModel.countriesState.test {
            // initial state
            assert(!awaitItem().isLoading)

            // loading state from onStart
            assert(awaitItem().isLoading)

            // success state
            val success = awaitItem()
            assert(!success.isLoading)
            assert(success.countries.isNotEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchCountyList emits error state`() = runTest {
        whenever(repository.fetchCounties()).thenReturn(
            flow {
                emit(GenericResult.Error("Network error"))
            }
        )

        viewModel = CountriesVM(repository)

        viewModel.countriesState.test {
            assert(!awaitItem().isLoading)
            assert(awaitItem().isLoading) // from onStart
            val errorState = awaitItem()
            assert(errorState.isErrorState)
            assert(errorState.error == "Network error")
            cancelAndIgnoreRemainingEvents()
        }
    }
}