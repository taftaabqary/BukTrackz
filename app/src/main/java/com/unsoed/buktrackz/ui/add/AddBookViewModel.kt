package com.unsoed.buktrackz.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsoed.buktrackz.core.domain.entity.Book
import kotlinx.coroutines.launch

class AddBookViewModel(private val bookUseCase: com.unsoed.buktrackz.core.domain.usecase.BookUseCase): ViewModel() {

    private var _result = MutableLiveData<Long>()
    val result: LiveData<Long> = _result

    fun insertNewBook(bookData: Book) {
        viewModelScope.launch {
            _result.value = bookUseCase.insertBook(bookData)
        }
    }
}