package com.example.tvseriesapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowPagingSource @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val query: String
    ): PagingSource<Int, TvShow>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val position = params.key ?: Constants.STARTING_PAGE_INDEX
            val shows = tvShowRepository.getShows(query = query, page = position)

            val prevKey = if (position == Constants.STARTING_PAGE_INDEX) null else position.minus(1)
            val nextKey = if (shows.isEmpty() || query.isNotEmpty()) null else position.plus(1)

            LoadResult.Page(
                data = shows,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}