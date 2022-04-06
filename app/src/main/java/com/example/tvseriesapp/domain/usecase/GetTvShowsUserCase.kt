package com.example.tvseriesapp.domain.usecase

import androidx.paging.PagingData
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowsUserCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    operator fun invoke(query: String): Flow<PagingData<TvShow>> = tvShowRepository.getShows(query)
}