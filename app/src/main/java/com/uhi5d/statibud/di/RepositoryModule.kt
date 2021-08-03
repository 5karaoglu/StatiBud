package com.uhi5d.statibud.di

import com.uhi5d.statibud.data.local.LocalDataSource
import com.uhi5d.statibud.data.remote.RemoteDataSource
import com.uhi5d.statibud.data.repository.RepositoryImpl
import com.uhi5d.statibud.domain.repository.Repository
import com.uhi5d.statibud.domain.usecase.Interactor
import com.uhi5d.statibud.domain.usecase.UseCase
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
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideUseCase(
        repositoryImpl: RepositoryImpl
    ): UseCase {
        return Interactor(repositoryImpl)
    }

}