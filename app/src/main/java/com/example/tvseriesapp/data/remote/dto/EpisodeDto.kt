package com.example.tvseriesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    val id: Int,
    val name: String?,
    @SerializedName("airdate")
    val airDate: String?,
    @SerializedName("airstamp")
    val airStamp: String?,
    val airtime: String?,
    val image: ImageDto?,
    @SerializedName("_links")
    val links: LinksDto?,
    val number: Int,
    val rating: RatingDto?,
    val runtime: Int,
    val season: Int,
    val summary: String?,
    val type: String?,
    val url: String?
)