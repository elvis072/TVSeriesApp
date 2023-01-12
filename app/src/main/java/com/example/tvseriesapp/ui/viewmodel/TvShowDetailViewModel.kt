package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.model.TvShowDetail
import com.example.tvseriesapp.domain.usecase.GetTvShowDetailUseCase
import com.example.tvseriesapp.domain.usecase.GetTvShowEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val getTvShowEpisodesUseCase: GetTvShowEpisodesUseCase
) : ViewModel() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _showDetailState = MutableStateFlow(TvShowDetailState())
    private val _showEpisodesState = MutableStateFlow(TvShowEpisodesState())

    val showDetailState: StateFlow<TvShowDetailState>
        get() = _showDetailState

    val showEpisodesState: StateFlow<TvShowEpisodesState>
        get() = _showEpisodesState

    init {
        refresh()
    }

    private fun getShow(id: Int) {
        getTvShowDetailUseCase(id)
            .flowOn(dispatcher)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> _showDetailState.value =
                        TvShowDetailState(isLoading = true)
                    is Result.Success -> _showDetailState.value =
                        TvShowDetailState(showDetail = result.data)
                    is Result.Error -> _showDetailState.value = TvShowDetailState()
                }
            }.launchIn(viewModelScope)

        getTvShowEpisodesUseCase(id)
            .flowOn(dispatcher)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> _showEpisodesState.value =
                        TvShowEpisodesState(isLoading = true)
                    is Result.Success -> _showEpisodesState.value =
                        TvShowEpisodesState(episodes = result.data ?: emptyList())
                    is Result.Error -> _showEpisodesState.value = TvShowEpisodesState()
                }
            }.launchIn(viewModelScope)
    }

    fun refresh() {
        savedStateHandle.get<Int>(Constants.TV_SHOW_ID_KEY)?.let {
            getShow(it)
        }
    }

    data class TvShowDetailState(
        val isLoading: Boolean = false,
        val showDetail: TvShowDetail? = null
    )

    data class TvShowEpisodesState(
        val isLoading: Boolean = false,
        val episodes: List<Episode> = emptyList()
    )
}