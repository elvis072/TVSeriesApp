package com.example.tvseriesapp.domain.repository

import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.model.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getShows(query: String, page: Int): List<TvShow>
    fun getShow(id: Int): Flow<Result<TvShowDetail>>
    fun getEpisodes(showId: Int): Flow<Result<List<Episode>>>
    fun getEpisode(showId: Int, season: Int, number: Int): Flow<Result<Episode>>
}