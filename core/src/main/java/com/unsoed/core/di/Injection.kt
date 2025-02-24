package com.unsoed.core.di

import android.content.Context
import com.unsoed.core.domain.usecase.BookUseCase

//object Injection {
//    private fun provideBookRepository(context: Context): IBookRepository {
//        val database = BookDatabase.getInstance(context)
//        val dao = database.bookDao()
//
//        return BookRepository(dao)
//    }
//
//    fun provideBookUseCase(context: Context): BookUseCase {
//        val bookRepository = provideBookRepository(context)
//        return BookInteractor(bookRepository)
//    }
//}