package com.uhi5d.statibud.di

import android.content.Context
import androidx.room.Room
import com.uhi5d.statibud.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()


    @Singleton
    @Provides
    fun provideGenresDao(db: AppDatabase) = db.genresDao()




}