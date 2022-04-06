package com.example.tvseriesapp.data.remote.service

import com.example.tvseriesapp.data.remote.dto.EpisodeDto
import com.example.tvseriesapp.data.remote.dto.TvShowDto
import com.example.tvseriesapp.data.remote.dto.TvShowSearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): List<TvShowDto>

    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<TvShowSearchDto>

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): TvShowDto

    @GET("shows/{showId}/episodes")
    suspend fun getShowEpisodes(@Path("showId") showId: Int): List<EpisodeDto>

    @GET("shows/{showId}/episodebynumber")
    suspend fun getShowEpisodes(@Path("showId") showId: Int, @Query("season") season: Int, @Query("number") number: Int): EpisodeDto
}