package com.unsoed.buktrackz.domain.usecase

import androidx.paging.PagingData
import com.unsoed.buktrackz.domain.entity.Book
import com.unsoed.buktrackz.domain.repository.IBookRepository
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

    override fun deleteBook(book: Book) {
        iBookRepository.deleteBook(book)
    }

    override fun setFavoriteBook(id: Int, isFavorite: Boolean) {
        iBookRepository.setFavoriteBook(id, isFavorite)
    }
}