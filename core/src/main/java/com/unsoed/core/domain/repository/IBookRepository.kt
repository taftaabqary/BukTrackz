package com.unsoed.core.domain.repository

import androidx.paging.PagingData
import com.unsoed.core.domain.entity.Book
import kotlinx.coroutines.flow.Flow

interface IBookRepository {

    fun getAllBook(): Flow<PagingData<com.unsoed.core.domain.entity.Book>>

    fun getFavoriteBook(): Flow<PagingData<com.unsoed.core.domain.entity.Book>>

    suspend fun insertBook(book: com.unsoed.core.domain.entity.Book): Long

    suspend fun deleteBook(book: com.unsoed.core.domain.entity.Book)

    suspend fun setFavoriteBook(id: Int, isFavorite: Boolean)

    suspend fun updateBook(book: com.unsoed.core.domain.entity.Book)

    fun getDetailBookById(id: Int): Flow<com.unsoed.core.domain.entity.Book>
}