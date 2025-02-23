package com.unsoed.buktrackz.di

import android.content.Context
import com.unsoed.buktrackz.data.local.BookDatabase
import com.unsoed.buktrackz.data.repository.BookRepository
import com.unsoed.buktrackz.domain.repository.IBookRepository
import com.unsoed.buktrackz.domain.usecase.BookInteractor
import com.unsoed.buktrackz.domain.usecase.BookUseCase

object Injection {
    private fun provideBookRepository(context: Context): IBookRepository {
        val database = BookDatabase.getInstance(context)
        val dao = database.bookDao()

        return BookRepository(dao)
    }

    fun provideBookUseCase(context: Context): BookUseCase {
        val bookRepository = provideBookRepository(context)
        return BookInteractor(bookRepository)
    }
}