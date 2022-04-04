package com.example.tvseriesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TvShowDto(
    val id: Int,
    val name: String?,
    val averageRuntime: Int,
    val dvdCountry: Any?,
    val ended: String?,
    val externals: ExternalsDto?,
    val genres: List<String>?,
    val image: ImageDto?,
    val language: String?,
    @SerializedName("_links")
    val links: LinksDto?,
    val network: NetworkDto?,
    val officialSite: String?,
    val premiered: String?,
    val rating: RatingDto?,
    val runtime: Int,
    val schedule: ScheduleDto?,
    val status: String?,
    val summary: String?,
    val type: String?,
    val updated: Int,
    val url: String?,
    val webChannel: WebChannelDto?,
    val weight: Int
)