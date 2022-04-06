package com.example.tvseriesapp.domain.repository

import androidx.paging.PagingData
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.model.TvShowDetail
import kotlinx.coroutines.flow.Flow
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode

interface TvShowRepository {
    fun getShows(query: String): Flow<PagingData<TvShow>>
    fun getShow(id: Int): Flow<Result<TvShowDetail>>
    fun getEpisodes(showId: Int): Flow<Result<List<Episode>>>
    fun getEpisode(showId: Int, season: Int, number: Int): Flow<Result<Episode>>
}