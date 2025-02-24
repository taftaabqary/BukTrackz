package com.unsoed.core.domain.usecase

import androidx.paging.PagingData
import com.unsoed.core.domain.entity.Book
import kotlinx.coroutines.flow.Flow

interface BookUseCase {
    fun getAllBook(): Flow<PagingData<Book>>
    fun getFavoriteBook(): Flow<PagingData<Book>>
    fun getDetailBookById(id: Int): Flow<Book>
    suspend fun insertBook(book: Book): Long
    suspend fun deleteBook(book: Book)
    suspend fun setFavoriteBook(id: Int, isFavorite: Boolean)
    suspend fun updateBook(book: Book)
}