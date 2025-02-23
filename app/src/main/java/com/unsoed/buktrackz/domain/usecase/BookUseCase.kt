package com.unsoed.buktrackz.domain.usecase

import androidx.paging.PagingData
import com.unsoed.buktrackz.domain.entity.Book
import kotlinx.coroutines.flow.Flow

interface BookUseCase {
    fun getAllBook(): Flow<PagingData<Book>>
    fun getFavoriteBook(): Flow<PagingData<Book>>
    suspend fun insertBook(book: Book): Long
    fun deleteBook(book: Book)
    fun setFavoriteBook(id: Int, isFavorite: Boolean)
}