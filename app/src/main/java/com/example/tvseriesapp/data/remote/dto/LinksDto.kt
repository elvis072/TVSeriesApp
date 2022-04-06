package com.example.tvseriesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("previousepisode")
    val previousEpisode: PreviousEpisodeDto?,
    val self: SelfDto?
)