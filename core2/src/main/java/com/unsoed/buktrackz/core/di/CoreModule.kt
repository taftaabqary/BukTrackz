package com.unsoed.buktrackz.core.di

import androidx.room.Room
import com.unsoed.buktrackz.core.data.local.BookDatabase
import com.unsoed.buktrackz.core.data.local.Preferences
import com.unsoed.buktrackz.core.data.local.dataStore
import com.unsoed.buktrackz.core.data.network.ApiService
import com.unsoed.buktrackz.core.data.repository.BookRepository
import com.unsoed.buktrackz.core.domain.repository.IBookRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

val dataStoreModule = module {
    single {
        Preferences(androidContext().dataStore)
    }
}

val retrofitModule = module {
    factory {
        val baseUrl = "https://api.nytimes.com/svc/books/v3/"
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single<IBookRepository> {
        BookRepository(get(), get(), get())
    }
}