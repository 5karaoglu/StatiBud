package com.uhi5d.spotibud

import android.content.Context
import androidx.room.Room
import com.uhi5d.spotibud.repository.ApiService
import com.uhi5d.spotibud.repository.AppDatabase
import com.uhi5d.spotibud.repository.Repository
import com.uhi5d.spotibud.repository.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object Module {
    private val BASE_URL = "https://api.spotify.com/v1/"

    @Singleton
    @Provides
    fun repositoryModule(searchHistoryDao: SearchHistoryDao, apiService: ApiService) =
        Repository(searchHistoryDao, apiService)


    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()


    @Singleton
    @Provides
    fun provideShDao(db: AppDatabase) = db.shDao()

    @Singleton
    @Provides
    fun provideTftDao(db: AppDatabase) = db.tftDao()


}