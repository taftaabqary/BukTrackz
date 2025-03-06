package com.unsoed.buktrackz.core.domain.usecase

import androidx.paging.PagingData
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller
import com.unsoed.buktrackz.core.domain.repository.IBookRepository
import com.unsoed.buktrackz.core.utils.Filter
import com.unsoed.buktrackz.core.utils.ListBook
import kotlinx.coroutines.flow.Flow

class BookInteractor(private val iBookRepository: IBookRepository): BookUseCase {
    override fun getAllBook(filter: Filter): Flow<PagingData<Book>> {
        return iBookRepository.getAllBook(filter)
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

    override suspend fun updateBook(book: Book): Int {
        return iBookRepository.updateBook(book)
    }

    override fun getBestSellerBook(list: ListBook): Flow<PagingData<BookBestSeller>> {
        return iBookRepository.getBestSellerBook(list)
    }

    override fun getDisplayUser(): Flow<Boolean> {
        return iBookRepository.getDisplayUser()
    }

    override suspend fun saveDisplayUser(isDisplay: Boolean) {
        iBookRepository.saveDisplayUser(isDisplay)
    }

    override fun getLanguageUser(): Flow<String> {
        return iBookRepository.getLanguageUser()
    }

    override suspend fun saveLanguageUser(language: String) {
        iBookRepository.saveLanguageUser(language)
    }

    override fun getDetailBookById(id: Int): Flow<Book> {
        return iBookRepository.getDetailBookById(id)
    }
}