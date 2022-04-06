package com.example.tvseriesapp.domain.usecase

import com.example.tvseriesapp.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowDetailUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    operator fun invoke(id: Int) = tvShowRepository.getShow(id)
}