package com.unsoed.buktrackz.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.utils.Event
import kotlinx.coroutines.launch

class AddBookViewModel(private val bookUseCase: com.unsoed.buktrackz.core.domain.usecase.BookUseCase): ViewModel() {

    private var _result = MutableLiveData<Event<Long>>()
    val result: LiveData<Event<Long>> = _result

    fun insertNewBook(bookData: Book) {
        viewModelScope.launch {
            _result.value = Event(bookUseCase.insertBook(bookData))
        }
    }
}