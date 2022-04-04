package com.example.tvseriesapp.data.remote.service

import com.example.tvseriesapp.data.remote.dto.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): List<TvShowDto>
}