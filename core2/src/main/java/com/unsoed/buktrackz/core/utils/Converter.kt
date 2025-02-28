package com.unsoed.buktrackz.core.utils

import com.unsoed.buktrackz.core.data.local.BookEntity
import com.unsoed.buktrackz.core.data.response.BookBestSellerResponse
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller

object Converter {
    fun convertEntityToDomain(bookEntity: BookEntity): Book {
        return Book(
            id = bookEntity.id,
            title = bookEntity.title,
            author = bookEntity.author,
            genre = bookEntity.genre,
            totalPages = bookEntity.totalPages,
            currentPages = bookEntity.currentPages,
            rate = bookEntity.rate,
            lastRead = bookEntity.lastRead,
            note = bookEntity.note,
            isFavorite = bookEntity.isFavorite,
            image = bookEntity.image
        )
    }

    fun convertDomainToEntity(bookDomain: Book): BookEntity {
        return BookEntity(
            id = bookDomain.id,
            title = bookDomain.title,
            author = bookDomain.author,
            genre = bookDomain.genre,
            totalPages = bookDomain.totalPages,
            currentPages = bookDomain.currentPages,
            rate = bookDomain.rate,
            lastRead = bookDomain.lastRead,
            note = bookDomain.note,
            isFavorite = bookDomain.isFavorite,
            image = bookDomain.image
        )
    }

    fun convertResponseToDomain(listBook: BookBestSellerResponse): List<BookBestSeller> {
        val listBookSeller = mutableListOf<BookBestSeller>()

        listBook.results?.books?.forEach { item ->
            listBookSeller.add(
                BookBestSeller(
                    contributor = item.contributor ?: "",
                    amazonProductUrl = item.amazonProductUrl ?: "",
                    author = item.author ?: "",
                    bookUri = item.bookUri ?: "",
                    bookImage = item.bookImage ?: "",
                    rank = item.rank ?: 0,
                    publisher = item.publisher ?: "",
                    description = item.description ?: "",
                    title = item.title ?: "",
                    bestSellerDate = listBook.results.bestsellersDate ?: "",
                    primaryIsbn13 = item.primaryIsbn13 ?: "",
                    displayName = listBook.results.displayName ?: "",
                )
            )
        }

        return listBookSeller
    }
}