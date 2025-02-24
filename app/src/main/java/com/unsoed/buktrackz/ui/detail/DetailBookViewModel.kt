package com.unsoed.buktrackz.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.unsoed.core.domain.entity.Book
import com.unsoed.core.domain.usecase.BookUseCase
import kotlinx.coroutines.launch

class DetailBookViewModel(private val useCase: com.unsoed.core.domain.usecase.BookUseCase): ViewModel() {
    private var _bookId = MutableLiveData<Int>()

    private var _book = _bookId.switchMap { id ->
        useCase.getDetailBookById(id).asLiveData()
    }

    val book: LiveData<com.unsoed.core.domain.entity.Book> = _book

    fun getBookId(id: Int) {
        if(_bookId.value != id) {
            _bookId.value = id
        } else {
            return
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            _book.value?.let {
                useCase.deleteBook(it)
            }
        }
    }

    fun updateFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            _book.value?.let {
                useCase.setFavoriteBook(it.id, isFavorite)
            }
        }
    }
}