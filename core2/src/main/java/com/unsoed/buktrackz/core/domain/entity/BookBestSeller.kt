package com.unsoed.buktrackz.core.domain.entity

data class BookBestSeller(
    val contributor: String,
    val amazonProductUrl: String,
    val author: String,
    val bookUri: String,
    val bookImage: String,
    val rank: Int,
    val publisher: String,
    val description: String,
    val title: String,
    val bestSellerDate: String,
    val primaryIsbn13: String,
    val displayName: String
)
