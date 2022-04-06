package com.example.tvseriesapp.data.remote.dto

data class NetworkDto(
    val country: CountryDto?,
    val id: Int,
    val name: String?,
    val officialSite: String?
)