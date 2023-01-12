package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.data.datasource.TvShowPagingSource
import com.example.tvseriesapp.domain.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    tvShowRepository: TvShowRepository
) : ViewModel() {
    private var query: String = ""
    private var pagingSource: TvShowPagingSource? = null

    val tvShows = Pager(
        config = PagingConfig(
            pageSize = Constants.NETWORK_PAGE_SIZE,
            initialLoadSize = Constants.NETWORK_PAGE_SIZE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            TvShowPagingSource(tvShowRepository, query).also { pagingSource = it }
        }
    ).flow
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)

    fun getShows(query: String) {
        this.query = query
        pagingSource?.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        pagingSource = null
    }
}