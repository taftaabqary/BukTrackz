package com.unsoed.buktrackz.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase
import com.unsoed.buktrackz.core.utils.ListBook

class DiscoverViewModel(private val bookUseCase: BookUseCase): ViewModel() {

    private var _typeBook: MutableLiveData<ListBook> = MutableLiveData()
    val typeBook = _typeBook.switchMap {
        bookUseCase.getBestSellerBook(it).asLiveData().cachedIn(viewModelScope)
    }

    fun changeTypeBook(listBook: ListBook) {
        _typeBook.value = listBook
    }
}