package com.unsoed.buktrackz.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.unsoed.buktrackz.data.local.BookDao
import com.unsoed.buktrackz.domain.entity.Book
import com.unsoed.buktrackz.domain.repository.IBookRepository
import com.unsoed.buktrackz.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao
): IBookRepository {

    override fun getAllBook(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = PLACEHOLDERS,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                bookDao.getAllBook()
            }
        ).flow.map { pagingData ->
            pagingData.map { bookEntity ->
                Converter.convertEntityToDomain(bookEntity)
            }
        }
    }

    override fun getFavoriteBook(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = PLACEHOLDERS,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                bookDao.getFavoriteBook()
            }
        ).flow.map { pagingData ->
            pagingData.map { bookEntity ->
                Converter.convertEntityToDomain(bookEntity)
            }
        }
    }

    override suspend fun insertBook(book: Book): Long {
        val bookEntity = Converter.convertDomainToEntity(book)
        return bookDao.insertNewBook(bookEntity)
    }

    override fun deleteBook(book: Book) {
        val bookEntity = Converter.convertDomainToEntity(book)
        bookDao.deleteBook(bookEntity)
    }

    override fun setFavoriteBook(id: Int, isFavorite: Boolean) {
        bookDao.updateBook(id, isFavorite)
    }

    companion object {
        const val PAGE_SIZE = 5
        const val PLACEHOLDERS = true

        private var INSTANCE: BookRepository? = null

        fun getInstance(bookDao: BookDao): BookRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = BookRepository(bookDao)
                INSTANCE = instance
                instance
            }
        }
    }
}