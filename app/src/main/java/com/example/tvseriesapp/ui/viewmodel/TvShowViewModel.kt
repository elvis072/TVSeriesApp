package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.usecase.GetTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val getTvShowsUseCase: GetTvShowsUseCase)
    : ViewModel() {

    fun getShows(query: String): Flow<PagingData<TvShow>> {
        return getTvShowsUseCase(query).cachedIn(viewModelScope)
    }
}