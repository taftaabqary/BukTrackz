package com.unsoed.buktrackz.core.data.network

import com.unsoed.buktrackz.core.data.response.BookBestSellerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("lists/{date}/{list}.json")
    suspend fun getBestSelleBook(
        @Path("date") date: String,
        @Path("list") list: String,
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int,
    ): BookBestSellerResponse
}