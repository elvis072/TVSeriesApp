package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ExternalsDto(
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int
)