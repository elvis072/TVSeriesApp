package com.example.tvseriesapp.data.repository

import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.data.remote.service.TvShowService
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.model.TvShowDetail
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(private val tvShowService: TvShowService)
    : TvShowRepository {
    override suspend fun getShows(query: String, page: Int): List<TvShow> {
        return if (query.isEmpty()) {
            tvShowService.getShows(page)
        } else {
            tvShowService.searchShows(query).map { it.show }
        }.map {
            TvShow(
                id = it.id,
                name = it.name ?: "",
                premiered = it.premiered ?: "",
                image = it.image?.medium ?: "",
                language = it.language ?: "",
                rating = it.rating?.average ?: 0f,
                runtime = it.runtime
            )
        }
    }

    override fun getShow(id: Int): Flow<Result<TvShowDetail>> = flow {
        try {
            emit(Result.Loading())
            val show = tvShowService.getShow(id)
            emit(Result.Success(TvShowDetail(
                id = show.id,
                name = show.name ?: "",
                image = show.image?.original ?: "",
                days = show.schedule?.days ?: emptyList(),
                time = show.schedule?.time ?: "",
                genres = show.genres ?: emptyList(),
                summary = show.summary ?: "",
                language = show.language ?: "",
                rating = show.rating?.average ?: 0f,
                runtime = show.runtime
            )))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        }
    }

    override fun getEpisodes(showId: Int): Flow<Result<List<Episode>>> = flow {
        try {
            emit(Result.Loading())
            val episodes = tvShowService.getShowEpisodes(showId)
            emit(Result.Success(
                episodes.map { episode ->
                    Episode(
                        id = episode.id,
                        number = episode.number,
                        season = episode.season,
                        name = episode.name ?: "",
                        image = episode.image?.original ?: "",
                        summary = episode.summary ?: "",
                        rating = episode.rating?.average ?: 0f,
                        runtime = episode.runtime,
                    )
                }
            ))
        }
        catch (e: Exception) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        }
    }

    override fun getEpisode(showId: Int, season: Int, number: Int): Flow<Result<Episode>> = flow {
        try {
            emit(Result.Loading())
            val episode = tvShowService.getShowEpisode(showId, season, number)
            emit(Result.Success(
                Episode(
                    id = episode.id,
                    number = episode.number,
                    season = episode.season,
                    name = episode.name ?: "",
                    image = episode.image?.original ?: "",
                    summary = episode.summary ?: "",
                    rating = episode.rating?.average ?: 0f,
                    runtime = episode.runtime,
                )
            ))
        }
        catch (e: Exception) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        }
    }
}