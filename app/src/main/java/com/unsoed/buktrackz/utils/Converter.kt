package com.unsoed.buktrackz.utils

import com.unsoed.buktrackz.data.local.BookEntity
import com.unsoed.buktrackz.domain.entity.Book

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
            isFavorite = bookEntity.isFavorite
        )
    }

    fun convertDomainToEntity(bookDomain: Book): BookEntity {
        return BookEntity(
            title = bookDomain.title,
            author = bookDomain.author,
            genre = bookDomain.genre,
            totalPages = bookDomain.totalPages,
            currentPages = bookDomain.currentPages,
            rate = bookDomain.rate,
            lastRead = bookDomain.lastRead,
            note = bookDomain.note,
            isFavorite = bookDomain.isFavorite
        )
    }
}