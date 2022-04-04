package com.example.tvseriesapp.domain.model

data class TvShow(
    val id: Int,
    val name: String,
    val premiered: String,
    val image: String,
    val language: String,
    val rating: Double,
    val runtime: Int
)