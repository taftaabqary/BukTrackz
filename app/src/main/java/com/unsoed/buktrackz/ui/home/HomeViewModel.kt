package com.unsoed.buktrackz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.unsoed.core.domain.entity.Book
import com.unsoed.core.domain.usecase.BookUseCase

class HomeViewModel(private val bookUseCase: com.unsoed.core.domain.usecase.BookUseCase) : ViewModel() {
    fun getAllBook(): LiveData<PagingData<com.unsoed.core.domain.entity.Book>> {
        return bookUseCase.getAllBook().asLiveData()
    }

    fun getFavoriteBook(): LiveData<PagingData<com.unsoed.core.domain.entity.Book>> {
        return bookUseCase.getFavoriteBook().asLiveData()
    }
}