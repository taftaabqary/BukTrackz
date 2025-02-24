package com.unsoed.core.di

import androidx.room.Room
import com.unsoed.core.data.local.BookDatabase
import com.unsoed.core.data.repository.BookRepository
import com.unsoed.core.domain.repository.IBookRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory {
        get<BookDatabase>().bookDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            BookDatabase::class.java,
            "book.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    single<IBookRepository> {
        BookRepository.getInstance(get())
    }
}