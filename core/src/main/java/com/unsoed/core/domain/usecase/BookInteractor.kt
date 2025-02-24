package com.unsoed.core.domain.usecase

import androidx.paging.PagingData
import com.unsoed.core.domain.entity.Book
import com.unsoed.core.domain.repository.IBookRepository
import kotlinx.coroutines.flow.Flow

class BookInteractor(private val iBookRepository: IBookRepository): BookUseCase {
    override fun getAllBook(): Flow<PagingData<Book>> {
        return iBookRepository.getAllBook()
    }

    override fun getFavoriteBook(): Flow<PagingData<Book>> {
        return iBookRepository.getFavoriteBook()
    }

    override suspend fun insertBook(book: Book): Long {
        return iBookRepository.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        iBookRepository.deleteBook(book)
    }

    override suspend fun setFavoriteBook(id: Int, isFavorite: Boolean) {
        iBookRepository.setFavoriteBook(id, isFavorite)
    }

    override suspend fun updateBook(book: Book) {
        iBookRepository.updateBook(book)
    }

    override fun getDetailBookById(id: Int): Flow<Book> {
        return iBookRepository.getDetailBookById(id)
    }
}