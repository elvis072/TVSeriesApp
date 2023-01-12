package com.example.tvseriesapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.usecase.GetTvShowEpisodeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TvShowEpisodeDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTvShowEpisodeDetailUseCase: GetTvShowEpisodeDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowEpisodeState())

    val state: StateFlow<TvShowEpisodeState>
        get() = _state

    init {
        refresh()
    }

    private fun getEpisode(showId: Int, season: Int, number: Int) {
        getTvShowEpisodeDetailUseCase(showId, season, number)
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> _state.value = TvShowEpisodeState(isLoading = true)
                    is Result.Success -> _state.value = TvShowEpisodeState(
                        isLoading = false,
                        episode = result.data
                    )
                    is Result.Error -> _state.value = TvShowEpisodeState()
                }
            }.launchIn(viewModelScope)
    }

    fun refresh() {
        savedStateHandle.get<Int>(Constants.TV_SHOW_ID_KEY)?.let { showId ->
            savedStateHandle.get<Int>(Constants.TV_SHOW_SEASON_ID_KEY)?.let { seasonId ->
                savedStateHandle.get<Int>(Constants.TV_SHOW_EPISODE_KEY)?.let { episodeId ->
                    getEpisode(showId, seasonId, episodeId)
                }
            }
        }
    }

    data class TvShowEpisodeState(
        val isLoading: Boolean = false,
        val episode: Episode? = null
    )
}