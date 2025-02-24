package com.unsoed.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.unsoed.core.data.local.BookDao
import com.unsoed.core.domain.repository.IBookRepository
import com.unsoed.core.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao
): IBookRepository {

    override fun getAllBook(): Flow<PagingData<com.unsoed.core.domain.entity.Book>> {
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

    override fun getFavoriteBook(): Flow<PagingData<com.unsoed.core.domain.entity.Book>> {
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

    override suspend fun insertBook(book: com.unsoed.core.domain.entity.Book): Long {
        val bookEntity = Converter.convertDomainToEntity(book)
        return bookDao.insertNewBook(bookEntity)
    }

    override suspend fun deleteBook(book: com.unsoed.core.domain.entity.Book) {
        val bookEntity = Converter.convertDomainToEntity(book)
        bookDao.deleteBook(bookEntity)
    }

    override suspend fun setFavoriteBook(id: Int, isFavorite: Boolean) {
        bookDao.updateBook(id, isFavorite)
    }

    override suspend fun updateBook(book: com.unsoed.core.domain.entity.Book) {
        bookDao.updateDetailBook(Converter.convertDomainToEntity(book))
    }

    override fun getDetailBookById(id: Int): Flow<com.unsoed.core.domain.entity.Book> {
        return bookDao.getDetailBookById(id)
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