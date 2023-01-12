package com.example.tvseriesapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.data.episode
import com.example.tvseriesapp.data.tvShowDetail
import com.example.tvseriesapp.domain.usecase.GetTvShowDetailUseCase
import com.example.tvseriesapp.domain.usecase.GetTvShowEpisodesUseCase
import com.example.tvseriesapp.rule.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowDetailViewModelTest {
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getTvShowDetailUseCase: GetTvShowDetailUseCase
    private lateinit var getTvShowEpisodesUseCase: GetTvShowEpisodesUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        getTvShowDetailUseCase = Mockito.mock(GetTvShowDetailUseCase::class.java)
        getTvShowEpisodesUseCase = Mockito.mock(GetTvShowEpisodesUseCase::class.java)
    }

    @Test
    fun `get tv show details with valid id, returns successful`() = testCoroutineRule.runTest {
        val showId = 1
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId

        `when`(getTvShowDetailUseCase(showId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Success(tvShowDetail))
        })

        val viewModel = TvShowDetailViewModel(
            savedStateHandle,
            getTvShowDetailUseCase,
            getTvShowEpisodesUseCase
        )

        viewModel.showDetailState.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNotNull(resultState.showDetail)

            expectNoEvents()
        }
    }

    @Test
    fun `get tv show episodes with valid id, returns successful`() = testCoroutineRule.runTest {
        val showId = 1
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId

        `when`(getTvShowEpisodesUseCase(showId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Success(listOf(episode)))
        })

        val viewModel = TvShowDetailViewModel(
            savedStateHandle,
            getTvShowDetailUseCase,
            getTvShowEpisodesUseCase
        )

        viewModel.showEpisodesState.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNotNull(resultState.episodes)

            expectNoEvents()
        }
    }

    @Test
    fun `get tv show details with invalid, returns error`() = testCoroutineRule.runTest {
        val showId = 0
        val errorMessage = "error"
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId

        `when`(getTvShowDetailUseCase(showId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Error(errorMessage))
        })

        val viewModel = TvShowDetailViewModel(
            savedStateHandle,
            getTvShowDetailUseCase,
            getTvShowEpisodesUseCase
        )

        viewModel.showDetailState.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNull(resultState.showDetail)

            expectNoEvents()
        }
    }

    @Test
    fun `get tv show episodes with invalid id, returns error`() = testCoroutineRule.runTest {
        val showId = 0
        val errorMessage = "error"
        savedStateHandle[Constants.TV_SHOW_ID_KEY] = showId

        `when`(getTvShowEpisodesUseCase(showId)).thenReturn(flow {
            emit(Result.Loading())
            emit(Result.Error(errorMessage))
        })

        val viewModel = TvShowDetailViewModel(
            savedStateHandle,
            getTvShowDetailUseCase,
            getTvShowEpisodesUseCase
        )

        viewModel.showEpisodesState.test {
            skipItems(1) // Skip default state

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val resultState = awaitItem()
            assertFalse(resultState.isLoading)
            assertNotNull(resultState.episodes)
            assertTrue(resultState.episodes.isEmpty())

            expectNoEvents()
        }
    }
}