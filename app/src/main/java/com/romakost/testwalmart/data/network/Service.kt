package com.romakost.testwalmart.data.network

import com.romakost.testwalmart.data.network.retrofit_adapters.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gist.githubusercontent.com/peymano-wmt/"

object Service {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }

    val countryApi: CountryApi by lazy {
        retrofit.create(CountryApi::class.java)
    }
}