package com.unsoed.core.utils

import com.unsoed.core.data.local.BookEntity
import com.unsoed.core.domain.entity.Book

object Converter {
    fun convertEntityToDomain(bookEntity: com.unsoed.core.data.local.BookEntity): com.unsoed.core.domain.entity.Book {
        return com.unsoed.core.domain.entity.Book(
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

    fun convertDomainToEntity(bookDomain: com.unsoed.core.domain.entity.Book): com.unsoed.core.data.local.BookEntity {
        return com.unsoed.core.data.local.BookEntity(
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