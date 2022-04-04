package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LinksDto(
    val previousepisode: PreviousepisodeDto,
    val self: SelfDto
)