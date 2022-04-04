package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class NetworkDto(
    val country: CountryDto,
    val id: Int,
    val name: String,
    val officialSite: String
)