package com.example.tvseriesapp.domain.usecase

import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowEpisodeDetailUserCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    operator fun invoke(showId: Int, season: Int, number: Int): Flow<Result<Episode>> =
        tvShowRepository.getEpisode(showId, season, number)
}