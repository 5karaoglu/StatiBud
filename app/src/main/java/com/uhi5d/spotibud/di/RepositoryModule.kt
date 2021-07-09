package com.uhi5d.spotibud.di

import com.uhi5d.spotibud.data.local.SearchHistoryDao
import com.uhi5d.spotibud.data.remote.WebService
import com.uhi5d.spotibud.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(searchHistoryDao: SearchHistoryDao, webService: WebService) =
        Repository(searchHistoryDao, webService)
}