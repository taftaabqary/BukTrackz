package com.unsoed.core.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val genre: String,
    val totalPages: Int,
    val currentPages: Int,
    val rate: Int,
    val lastRead: Long,
    val note: String,
    val isFavorite: Boolean
): Parcelable