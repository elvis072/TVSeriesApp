package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.usecase.GetTvShowsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val getTvShowsUserCase: GetTvShowsUserCase)
    : ViewModel() {

    fun getShows(query: String): Flow<PagingData<TvShow>> {
        return getTvShowsUserCase(query).cachedIn(viewModelScope)
    }
}