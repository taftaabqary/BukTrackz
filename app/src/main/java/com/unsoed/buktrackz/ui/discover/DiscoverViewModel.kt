package com.unsoed.buktrackz.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase
import com.unsoed.buktrackz.core.utils.ListBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel(private val bookUseCase: BookUseCase): ViewModel() {

    private var _arrayList: MutableStateFlow<Array<String>> = MutableStateFlow(emptyArray())
    val arrayList: StateFlow<Array<String>> = _arrayList

    private var _typeBook: MutableLiveData<ListBook> = MutableLiveData()
    val typeBook = _typeBook.switchMap {
        bookUseCase.getBestSellerBook(it).asLiveData().cachedIn(viewModelScope)
    }

    fun changeTypeBook(listBook: ListBook) {
        if(listBook != _typeBook.value) {
            _typeBook.value = listBook
        }
    }

    fun loadArrayListBook() {
        viewModelScope.launch {
            _arrayList.value = getArrayListBook()
        }
    }

    private fun getArrayListBook(): Array<String> {
        return ListBook.entries.map {
            it.display
        }.toTypedArray()
    }
}