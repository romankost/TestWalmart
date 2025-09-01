package com.romakost.testwalmart.data

import com.google.gson.annotations.SerializedName

data class CountryResponse  (
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("code")
    val code: String
)