package com.unsoed.buktrackz.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.unsoed.buktrackz.core.adapter.BookPagingSource
import com.unsoed.buktrackz.core.data.local.BookDao
import com.unsoed.buktrackz.core.data.local.Preferences
import com.unsoed.buktrackz.core.data.network.ApiService
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller
import com.unsoed.buktrackz.core.domain.repository.IBookRepository
import com.unsoed.buktrackz.core.utils.Converter
import com.unsoed.buktrackz.core.utils.ListBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao,
    private val apiService: ApiService,
    private val dataStore: Preferences
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

    override suspend fun deleteBook(book: Book) {
        val bookEntity = Converter.convertDomainToEntity(book)
        bookDao.deleteBook(bookEntity)
    }

    override suspend fun setFavoriteBook(id: Int, isFavorite: Boolean) {
        bookDao.updateBook(id, isFavorite)
    }

    override suspend fun updateBook(book: Book): Int {
        return bookDao.updateDetailBook(Converter.convertDomainToEntity(book))
    }

    override fun getDetailBookById(id: Int): Flow<Book> {
        return bookDao.getDetailBookById(id).map {
            Converter.convertEntityToDomain(it)
        }
    }

    override fun getBestSellerBook(list: ListBook): Flow<PagingData<BookBestSeller>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                BookPagingSource(apiService, list.key)
            }
        ).flow
    }

    override fun getDisplayUser(): Flow<Boolean> {
        return dataStore.getDisplayUser()
    }

    override suspend fun saveDisplayUser(isDisplay: Boolean) {
        return dataStore.saveDisplayUser(isDisplay)
    }

    companion object {
        const val PAGE_SIZE = 5
        const val PLACEHOLDERS = true
    }
}