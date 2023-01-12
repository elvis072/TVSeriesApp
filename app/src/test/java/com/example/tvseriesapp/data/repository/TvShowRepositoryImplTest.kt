package com.example.tvseriesapp.data.repository

import app.cash.turbine.test
import com.example.tvseriesapp.common.Result
import com.example.tvseriesapp.data.episodeDto
import com.example.tvseriesapp.data.remote.dto.EpisodeDto
import com.example.tvseriesapp.data.remote.dto.TvShowDto
import com.example.tvseriesapp.data.remote.dto.TvShowSearchDto
import com.example.tvseriesapp.data.remote.service.TvShowService
import com.example.tvseriesapp.data.tvShowDto
import com.example.tvseriesapp.domain.repository.TvShowRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class TvShowRepositoryImplTest {
    private lateinit var repository: TvShowRepository
    private lateinit var tvShowService: TvShowService

    @Before
    fun setUp() {
        tvShowService = mock(TvShowService::class.java)
        repository = TvShowRepositoryImpl(tvShowService)
    }

    @Test
    fun `get tv shows without query and valid page, returns successful`() = runBlocking {
        val successResponse = listOf(tvShowDto)
        val query = ""
        val page = 1

        `when`(tvShowService.getShows(Mockito.anyInt())).thenReturn(successResponse)

        val result = repository.getShows(query, page)

        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `get tv shows without query and invalid page, returns successful`() = runBlocking {
        val successResponse = emptyList<TvShowDto>()
        val query = ""
        val page = 0

        `when`(tvShowService.getShows(Mockito.anyInt())).thenReturn(successResponse)

        val result = repository.getShows(query, page)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `get tv shows with query and valid page, returns successful`() = runBlocking {
        val successResponse = listOf(TvShowSearchDto(0f, tvShowDto))
        val query = "Test"
        val page = 1

        `when`(tvShowService.searchShows(Mockito.anyString())).thenReturn(successResponse)

        val result = repository.getShows(query, page)

        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `get tv shows with query and invalid page, returns successful`() = runBlocking {
        val successResponse = listOf(TvShowSearchDto(0f, tvShowDto))
        val query = "Test"
        val page = 0

        `when`(tvShowService.searchShows(Mockito.anyString())).thenReturn(successResponse)

        val result = repository.getShows(query, page)

        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `get tv show details with valid id, returns successful`() = runBlocking {
        val successResponse = tvShowDto
        val showId = 1

        `when`(tvShowService.getShow(Mockito.anyInt())).thenReturn(successResponse)

        repository.getShow(showId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertNotNull(result.data)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get tv show details with invalid id, returns error`() = runBlocking {
        val errorResponse: TvShowDto? = null
        val showId = 0

        `when`(tvShowService.getShow(Mockito.anyInt())).thenReturn(errorResponse)

        repository.getShow(showId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertNull(result.data)
            assertNotNull(result.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get tv show episodes with valid id, returns successful`() = runBlocking {
        val successResponse = listOf(episodeDto)
        val showId = 1

        `when`(tvShowService.getShowEpisodes(Mockito.anyInt())).thenReturn(successResponse)

        repository.getEpisodes(showId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertNotNull(result.data)
            assertTrue(result.data!!.isNotEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get tv show episodes with invalid id, returns error`() = runBlocking {
        val errorResponse: List<EpisodeDto>? = null
        val showId = 0

        `when`(tvShowService.getShowEpisodes(Mockito.anyInt())).thenReturn(errorResponse)

        repository.getEpisodes(showId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertNull(result.data)
            assertNotNull(result.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get tv show episode details with valid ids, returns successful`() = runBlocking {
        val successResponse = episodeDto
        val showId = 1
        val seasonId = 1
        val episodeId = 1

        `when`(tvShowService.getShowEpisode(
            Mockito.anyInt(),
            Mockito.anyInt(),
            Mockito.anyInt(),
        )).thenReturn(successResponse)

        repository.getEpisode(showId, seasonId, episodeId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Success)
            assertNotNull(result.data)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get tv show episode details with invalid ids, returns error`() = runBlocking {
        val errorResponse: EpisodeDto? = null
        val showId = 0
        val seasonId = 0
        val episodeId = 0

        `when`(tvShowService.getShowEpisode(
            Mockito.anyInt(),
            Mockito.anyInt(),
            Mockito.anyInt(),
        )).thenReturn(errorResponse)

        repository.getEpisode(showId, seasonId, episodeId).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Result.Loading)

            val result = awaitItem()
            assertTrue(result is Result.Error)
            assertNull(result.data)
            assertNotNull(result.message)

            cancelAndConsumeRemainingEvents()
        }
    }
}