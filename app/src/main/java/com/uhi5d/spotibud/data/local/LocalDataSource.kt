package com.uhi5d.spotibud.data.local

import com.uhi5d.spotibud.data.local.entity.asGenres
import com.uhi5d.spotibud.domain.model.genres.Genres
import javax.inject.Inject

class LocalDataSource
@Inject constructor(
    private val genreDao: GenreDao
    ){

    suspend fun getAllGenres(): Genres = genreDao.getAllGenres()

    suspend fun saveGenres(list: List<String>) = genreDao.saveGenre(list.asGenres())
}