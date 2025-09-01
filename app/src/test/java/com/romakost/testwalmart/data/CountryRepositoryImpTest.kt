package com.romakost.testwalmart.data

import com.romakost.testwalmart.data.network.CountryApi
import com.romakost.testwalmart.data.network.NetworkResult
import com.romakost.testwalmart.data.network.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryImpTest {

    private lateinit var repository: CountryRepository
    private lateinit var mockApi: CountryApi
    private lateinit var mockService: Service

    @Before
    fun setUp() {
        mockApi = mock()
        mockService = mock()
        whenever(mockService.countryApi).thenReturn(mockApi)
        repository = CountryRepositoryImp(mockService)
    }

    @Test
    fun `fetchCounties returns Success mapped to GenericResult_Progress`() = runTest {
        // given
        val fakeCountries = listOf(CountryResponse(name = "Ukraine", region = "EU", capital = "Kiev", code = "UA" ))
        whenever(mockApi.fetchCountries()).thenReturn(NetworkResult.Success(fakeCountries))

        // when
        val result = repository.fetchCounties().first()

        // then
        assertTrue(result is GenericResult.Progress)
    }

    @Test
    fun `fetchCounties returns Success mapped to GenericResult_Success`() = runTest {
        // given
        val fakeCountries = listOf(CountryResponse(name = "Ukraine", region = "EU", capital = "Kiev", code = "UA" ))
        whenever(mockApi.fetchCountries()).thenReturn(NetworkResult.Success(fakeCountries))

        // when
        val result = repository.fetchCounties().drop(1).first()

        // then
        assertTrue(result is GenericResult.Success)
        val success = result as GenericResult.Success
        assertEquals(fakeCountries, success.data)
    }

    @Test
    fun `fetchCounties maps NetworkResult_Error to GenericResult_Error`() = runTest {
        // given
        whenever(mockApi.fetchCountries()).thenReturn(NetworkResult.Error(code = 404, message = "Error"))

        // when
        val result = repository.fetchCounties().drop(1).first()

        // then
        assertTrue(result is GenericResult.Error)
        val error = result as GenericResult.Error
        assertTrue(error.errorMessage.contains("not found", ignoreCase = true) ||
                error.errorMessage.isNotEmpty())
    }

    @Test
    fun `fetchCounties maps NetworkResult_Exception to GenericResult_Error`() = runTest {
        // given
        whenever(mockApi.fetchCountries()).thenReturn(NetworkResult.Exception(Exception("Exception")))

        // when
        val result = repository.fetchCounties().drop(1).first()

        // then
        assertTrue(result is GenericResult.Error)
        val error = result as GenericResult.Error
        assertEquals("An unexpected error occurred. Please try again.", error.errorMessage)
    }

}