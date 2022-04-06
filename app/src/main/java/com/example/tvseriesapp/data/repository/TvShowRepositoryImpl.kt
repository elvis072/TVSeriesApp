package com.example.tvseriesapp.data.repository

import androidx.paging.*
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.data.datasource.TvShowPagingSource
import com.example.tvseriesapp.data.remote.service.TvShowService
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.model.TvShowDetail
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode

class TvShowRepositoryImpl @Inject constructor(private val tvShowService: TvShowService)
    : TvShowRepository {
    override fun getShows(query: String): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TvShowPagingSource(tvShowService, query) }
        ).flow.map { pagingData ->
            pagingData.map {
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
        }
        catch (e: IOException) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        } catch (e: HttpException) {
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
        catch (e: IOException) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        } catch (e: HttpException) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        }
    }

    override fun getEpisode(showId: Int, season: Int, number: Int): Flow<Result<Episode>> = flow {
        try {
            emit(Result.Loading())
            val episode = tvShowService.getShowEpisodes(showId, season, number)
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
        catch (e: IOException) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        } catch (e: HttpException) {
            emit(Result.Error(e.message ?: Constants.GENERIC_ERROR))
        }
    }
}