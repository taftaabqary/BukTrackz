package com.unsoed.buktrackz.core.domain.repository

import androidx.paging.PagingData
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller
import com.unsoed.buktrackz.core.utils.ListBook
import kotlinx.coroutines.flow.Flow

interface IBookRepository {

    fun getAllBook(): Flow<PagingData<Book>>

    fun getFavoriteBook(): Flow<PagingData<Book>>

    suspend fun insertBook(book: Book): Long

    suspend fun deleteBook(book: Book)

    suspend fun setFavoriteBook(id: Int, isFavorite: Boolean)

    suspend fun updateBook(book: Book): Int

    fun getDetailBookById(id: Int): Flow<Book>

    fun getBestSellerBook(list: ListBook): Flow<PagingData<BookBestSeller>>

    fun getDisplayUser(): Flow<Boolean>

    suspend fun saveDisplayUser(isDisplay: Boolean)
}