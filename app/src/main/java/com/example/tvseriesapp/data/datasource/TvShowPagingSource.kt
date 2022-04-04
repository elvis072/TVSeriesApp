package com.example.tvseriesapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.data.remote.dto.TvShowDto
import com.example.tvseriesapp.data.remote.service.TvShowService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TvShowPagingSource @Inject constructor(private val tvShowService: TvShowService)
    : PagingSource<Int, TvShowDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowDto> {
        val position = params.key ?: Constants.STARTING_PAGE_INDEX
        return try {
            val shows = tvShowService.getShows(position)
            val nextKey = if (shows.isEmpty()) null else position.plus(1)
            LoadResult.Page(
                data = shows,
                prevKey = if (position == Constants.STARTING_PAGE_INDEX) null else - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}