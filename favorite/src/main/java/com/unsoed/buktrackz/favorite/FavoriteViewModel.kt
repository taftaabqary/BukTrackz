package com.unsoed.buktrackz.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase

class FavoriteViewModel(private val useCase: BookUseCase): ViewModel() {
    fun getBookFavorite(): LiveData<PagingData<Book>> {
        return useCase.getFavoriteBook().asLiveData()
    }
}