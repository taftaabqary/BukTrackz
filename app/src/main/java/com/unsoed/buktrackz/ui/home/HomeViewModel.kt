package com.unsoed.buktrackz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase
import com.unsoed.buktrackz.core.utils.Filter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class HomeViewModel(private val bookUseCase: BookUseCase) : ViewModel() {

    private val _filterType = MutableStateFlow(Filter.ALL)
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val books = _filterType
        .debounce(100)
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            bookUseCase.getAllBook(filter)
        }
        .cachedIn(viewModelScope)

    fun getFavoriteBook(): LiveData<PagingData<Book>> {
        return bookUseCase.getFavoriteBook().asLiveData()
    }

    fun changeFilter(filter: Filter) {
        _filterType.value = filter
    }
}