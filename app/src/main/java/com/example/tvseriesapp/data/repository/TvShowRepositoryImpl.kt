package com.example.tvseriesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.data.datasource.TvShowPagingSource
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(private val tvShowPagingSource: TvShowPagingSource)
    : TvShowRepository {
    override fun getShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { tvShowPagingSource }
        ).flow.map { pagingData ->
            pagingData.map {
                TvShow(
                    id = it.id,
                    name = it.name ?: "",
                    premiered = it.premiered ?: "",
                    image = it.image?.medium ?: "",
                    language = it.language ?: "",
                    rating = it.rating?.average ?: 0.0,
                    runtime = it.runtime
                )
            }
        }
    }
}