package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WebChannelDto(
    val country: Any,
    val id: Int,
    val name: String,
    val officialSite: Any
)