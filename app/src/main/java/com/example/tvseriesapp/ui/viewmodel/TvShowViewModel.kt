package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tvseriesapp.domain.usecase.GetTvShowsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val getTvShowsUserCase: GetTvShowsUserCase)
    : ViewModel() {
        val tvShows = getTvShowsUserCase().cachedIn(viewModelScope)
}