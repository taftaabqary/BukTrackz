package com.unsoed.buktrackz.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @RawQuery(observedEntities = [BookEntity::class])
    fun getAllBook(query: SupportSQLiteQuery) : PagingSource<Int, BookEntity>

    @Query("SELECT * FROM book WHERE isFavorite = 1")
    fun getFavoriteBook() : PagingSource<Int, BookEntity>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getDetailBookById(id: Int) : Flow<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBook(book: BookEntity): Long

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("UPDATE book SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateBook(id: Int, isFavorite: Boolean)

    @Update
    suspend fun updateDetailBook(book: BookEntity): Int
}