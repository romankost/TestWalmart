package com.romakost.testwalmart.data

import com.romakost.testwalmart.data.network.NetworkResult
import com.romakost.testwalmart.data.network.Service
import com.romakost.testwalmart.data.network.errorCodeToExceptionMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.VisibleForTesting

interface CountryRepository {
    suspend fun fetchCounties(): Flow<GenericResult<List<CountryResponse>>>
}

class CountryRepositoryImp @VisibleForTesting internal constructor(
    val service: Service = Service
): CountryRepository {

    override suspend fun fetchCounties(): Flow<GenericResult<List<CountryResponse>>> {
        return flow {
            emit(GenericResult.Progress())
            emit(service.countryApi.fetchCountries().mapToGenericResult())
        }
    }

    private fun NetworkResult<List<CountryResponse>>.mapToGenericResult(): GenericResult<List<CountryResponse>> {
         return when(this) {
            is NetworkResult.Exception -> GenericResult.Error("An unexpected error occurred. Please try again.")
            is NetworkResult.Error -> GenericResult.Error(code.errorCodeToExceptionMessage())
            is NetworkResult.Success -> GenericResult.Success(data = this.data)
        }
    }

    companion object {
        val instance: CountryRepository by lazy { CountryRepositoryImp() }
    }
}