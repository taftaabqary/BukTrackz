package com.unsoed.buktrackz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase

class MainViewModel(private val useCase: BookUseCase): ViewModel() {
    fun getDisplayUser(): LiveData<Boolean> {
        return useCase.getDisplayUser().asLiveData()
    }
}