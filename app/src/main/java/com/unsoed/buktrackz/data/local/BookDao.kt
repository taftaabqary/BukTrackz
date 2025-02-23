package com.unsoed.buktrackz.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBook() : PagingSource<Int, BookEntity>

    @Query("SELECT * FROM book WHERE isFavorite = 1")
    fun getFavoriteBook() : PagingSource<Int, BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBook(book: BookEntity): Long

    @Delete
    fun deleteBook(book: BookEntity)

    @Query("UPDATE book SET isFavorite = :isFavorite WHERE id = :id")
    fun updateBook(id: Int, isFavorite: Boolean)
}