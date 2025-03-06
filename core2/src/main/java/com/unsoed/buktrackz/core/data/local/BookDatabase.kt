package com.unsoed.buktrackz.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}