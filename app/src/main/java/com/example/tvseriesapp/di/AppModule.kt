package com.example.tvseriesapp.di

import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.data.datasource.TvShowPagingSource
import com.example.tvseriesapp.data.remote.service.TvShowService
import com.example.tvseriesapp.data.repository.TvShowRepositoryImpl
import com.example.tvseriesapp.domain.repository.TvShowRepository
import com.example.tvseriesapp.domain.usecase.GetTvShowsUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTvShowService() : TvShowService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowPagingSource(tvShowService: TvShowService) : TvShowPagingSource {
        return TvShowPagingSource(tvShowService)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(tvShowPagingSource: TvShowPagingSource) : TvShowRepository {
        return TvShowRepositoryImpl(tvShowPagingSource)
    }

    @Provides
    @Singleton
    fun provideGetShowsUseCase(tvShowRepository: TvShowRepository) : GetTvShowsUserCase {
        return GetTvShowsUserCase(tvShowRepository)
    }
}