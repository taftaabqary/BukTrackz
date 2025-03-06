package com.unsoed.buktrackz.core.di

import androidx.room.Room
import com.unsoed.buktrackz.core.data.local.BookDatabase
import com.unsoed.buktrackz.core.data.local.Preferences
import com.unsoed.buktrackz.core.data.local.dataStore
import com.unsoed.buktrackz.core.data.network.ApiService
import com.unsoed.buktrackz.core.data.repository.BookRepository
import com.unsoed.buktrackz.core.domain.repository.IBookRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<BookDatabase>().bookDao()
    }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("althaaf".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            BookDatabase::class.java,
            "book.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
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
        val hostname = "api.nytimes.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/EyIpNnV9CUcjjjlQOiouSnkMYsrs6Nc/W4OHs+k49B4=")
            .add(hostname, "sha256/lPraBjD+VHks5/sVEDOczhg00z9TGoGMAjndDyYGUNU=")
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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