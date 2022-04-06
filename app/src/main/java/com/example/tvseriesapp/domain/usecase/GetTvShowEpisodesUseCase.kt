package com.example.tvseriesapp.domain.usecase

import com.example.tvseriesapp.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowEpisodesUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    operator fun invoke(showId: Int) = tvShowRepository.getEpisodes(showId)
}