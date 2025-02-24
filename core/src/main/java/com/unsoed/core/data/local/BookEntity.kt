package com.unsoed.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int = 0,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "author")
    val author: String,

    @field:ColumnInfo(name = "genre")
    val genre: String,

    @field:ColumnInfo(name = "totalPages")
    val totalPages: Int,

    @field:ColumnInfo(name = "currentPages")
    val currentPages: Int,

    @field:ColumnInfo(name = "rate")
    val rate: Int,

    @field:ColumnInfo(name = "lastRead")
    val lastRead: Long,

    @field:ColumnInfo(name = "note")
    val note: String,

    @field:ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)
