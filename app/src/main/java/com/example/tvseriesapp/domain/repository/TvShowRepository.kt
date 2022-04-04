package com.example.tvseriesapp.domain.repository

import androidx.paging.PagingData
import com.example.tvseriesapp.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getShows(): Flow<PagingData<TvShow>>
}