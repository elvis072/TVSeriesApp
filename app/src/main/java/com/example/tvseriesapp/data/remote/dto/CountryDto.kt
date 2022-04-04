package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CountryDto(
    val code: String,
    val name: String,
    val timezone: String
)