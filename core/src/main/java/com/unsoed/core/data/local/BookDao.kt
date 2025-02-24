package com.unsoed.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsoed.core.domain.entity.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBook() : PagingSource<Int, com.unsoed.core.data.local.BookEntity>

    @Query("SELECT * FROM book WHERE isFavorite = 1")
    fun getFavoriteBook() : PagingSource<Int, com.unsoed.core.data.local.BookEntity>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getDetailBookById(id: Int) : Flow<com.unsoed.core.domain.entity.Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBook(book: com.unsoed.core.data.local.BookEntity): Long

    @Delete
    suspend fun deleteBook(book: com.unsoed.core.data.local.BookEntity)

    @Query("UPDATE book SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateBook(id: Int, isFavorite: Boolean)

    @Update
    suspend fun updateDetailBook(book: com.unsoed.core.data.local.BookEntity)
}