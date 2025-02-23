package com.unsoed.buktrackz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.unsoed.buktrackz.domain.entity.Book
import com.unsoed.buktrackz.domain.usecase.BookUseCase

class HomeViewModel(private val bookUseCase: BookUseCase) : ViewModel() {
    fun getAllBook(): LiveData<PagingData<Book>> {
        return bookUseCase.getAllBook().asLiveData()
    }

    fun getFavoriteBook(): LiveData<PagingData<Book>> {
        return bookUseCase.getFavoriteBook().asLiveData()
    }
}