package com.example.tvseriesapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.tvseriesapp.data.tvShow
import com.example.tvseriesapp.domain.repository.TvShowRepository
import com.example.tvseriesapp.rule.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowViewModelTest {
    private lateinit var tvShowRepository: TvShowRepository
    private lateinit var tvShowViewModel: TvShowViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        tvShowRepository = mock(TvShowRepository::class.java)
        tvShowViewModel = TvShowViewModel(tvShowRepository)
    }

    @Test
    fun `get tv shows with empty query, returns successful`() = testCoroutineRule.runTest {
        val successResponse = listOf(tvShow)

        `when`(tvShowRepository.getShows(
            Mockito.anyString(),
            Mockito.anyInt()
        )).thenReturn(successResponse)

        tvShowViewModel.tvShows.test {
            val show = awaitItem()
            assertNotNull(show)
        }
    }

    @Test
    fun `get tv shows with query, returns successful`() = testCoroutineRule.runTest {
        val successResponse = listOf(tvShow)
        val query = "Test"

        `when`(tvShowRepository.getShows(
            Mockito.anyString(),
            Mockito.anyInt()
        )).thenReturn(successResponse)

        tvShowViewModel.getShows(query)

        tvShowViewModel.tvShows.test {
            val show = awaitItem()
            assertNotNull(show)
        }
    }
}