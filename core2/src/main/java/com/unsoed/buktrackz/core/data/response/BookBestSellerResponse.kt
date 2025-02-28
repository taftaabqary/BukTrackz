package com.unsoed.buktrackz.core.data.response

import com.google.gson.annotations.SerializedName

data class BookBestSellerResponse(

	@field:SerializedName("copyright")
	val copyright: String? = null,

	@field:SerializedName("last_modified")
	val lastModified: String? = null,

	@field:SerializedName("results")
	val results: Results? = null,

	@field:SerializedName("num_results")
	val numResults: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Results(

	@field:SerializedName("bestsellers_date")
	val bestsellersDate: String? = null,

	@field:SerializedName("books")
	val books: List<BooksItem>? = null,

	@field:SerializedName("corrections")
	val corrections: List<Any?>? = null,

	@field:SerializedName("normal_list_ends_at")
	val normalListEndsAt: Int? = null,

	@field:SerializedName("list_name")
	val listName: String? = null,

	@field:SerializedName("list_name_encoded")
	val listNameEncoded: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("published_date")
	val publishedDate: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null
)

data class BooksItem(

	@field:SerializedName("contributor")
	val contributor: String? = null,

	@field:SerializedName("amazon_product_url")
	val amazonProductUrl: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("primary_isbn13")
	val primaryIsbn13: String? = null,

	@field:SerializedName("book_uri")
	val bookUri: String? = null,

	@field:SerializedName("book_image")
	val bookImage: String? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("publisher")
	val publisher: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
