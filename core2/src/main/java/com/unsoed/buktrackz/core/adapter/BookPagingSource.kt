package com.unsoed.buktrackz.core.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unsoed.buktrackz.core.data.network.ApiService
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller
import com.unsoed.buktrackz.core.utils.Converter

class BookPagingSource(
    private val apiService: ApiService,
    private val type: String
): PagingSource<Int, BookBestSeller>() {
    
    override fun getRefreshKey(state: PagingState<Int, BookBestSeller>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookBestSeller> {
        return try {
            val page = params.key ?: 1
            val offset = (page - 1) * 20

            val response = apiService.getBestSelleBook(
                apiKey = "WwMZWwqAh70Lio93YNJNL8xmDXyjGmxj",
                offset = offset,
                list = type,
                date = "current"
            )

            val responseList = Converter.convertResponseToDomain(response)

            LoadResult.Page(
                data = responseList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseList.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.d("BookPagingSource", e.message.toString())
            return LoadResult.Error(e)
        }
    }
}