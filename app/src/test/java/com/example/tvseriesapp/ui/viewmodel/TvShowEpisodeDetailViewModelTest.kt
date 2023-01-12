package com.example.tvseriesapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.data.episode
import com.example.tvseriesapp.domain.usecase.GetTvShowEpisodeDetailUseCase
import com.example.tvseriesapp.rule.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowEpisodeDetailViewModelTest {
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getTvShowEpisodeDetailUseCase: GetTvShowEpisodeDetailUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        getTvShowEpisodeDetailUseCase = mock(GetTvShowEpisodeDetailUseCase::class.java)
    }

    @Test
    fun `get tv show episode details with valid ids, returns successful`() = testCoroutineRule.runTest {
        val showId = 1
        val seasonId = 1
        val episodeId = 1
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId
        savedStateHandle[Constants.TV_SHOW_SEASON_ID_KEY] = seasonId
        savedStateHandle[Constants.TV_SHOW_EPISODE_KEY] = episodeId

        Mockito.`when`(getTvShowEpisodeDetailUseCase(showId, seasonId, episodeId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Success(episode))
        })

        val viewModel = TvShowEpisodeDetailViewModel(
            savedStateHandle,
            getTvShowEpisodeDetailUseCase
        )

        viewModel.state.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNotNull(resultState.episode)

            expectNoEvents()
        }
    }

    @Test
    fun `get tv show episode details with invalid ids, returns error`() = testCoroutineRule.runTest {
        val showId = 1
        val seasonId = 1
        val episodeId = 1
        val errorMessage = "error"
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId
        savedStateHandle[Constants.TV_SHOW_SEASON_ID_KEY] = seasonId
        savedStateHandle[Constants.TV_SHOW_EPISODE_KEY] = episodeId

        Mockito.`when`(getTvShowEpisodeDetailUseCase(showId, seasonId, episodeId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Error(errorMessage))
        })

        val viewModel = TvShowEpisodeDetailViewModel(
            savedStateHandle,
            getTvShowEpisodeDetailUseCase
        )

        viewModel.state.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNull(resultState.episode)

            expectNoEvents()
        }
    }
}