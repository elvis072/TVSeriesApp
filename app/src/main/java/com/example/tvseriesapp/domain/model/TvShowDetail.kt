package com.example.tvseriesapp.domain.model

data class TvShowDetail(
    val id: Int,
    val name: String,
    val image: String,
    val days: List<String>,
    val time: String,
    val genres: List<String>,
    val summary: String,
    val language: String,
    val rating: Float,
    val runtime: Int
)