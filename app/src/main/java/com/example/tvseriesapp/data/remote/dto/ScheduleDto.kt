package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ScheduleDto(
    val days: List<String>,
    val time: String
)