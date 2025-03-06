package com.unsoed.buktrackz.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.unsoed.buktrackz.core.domain.usecase.BookUseCase
import kotlinx.coroutines.launch

class SettingViewModel(private val useCase: BookUseCase): ViewModel() {
    fun getDisplayUser(): LiveData<Boolean> {
        return useCase.getDisplayUser().asLiveData()
    }

    fun saveDisplayUser(isDarkMode: Boolean) {
        viewModelScope.launch {
            useCase.saveDisplayUser(isDarkMode)
        }
    }

    fun getLanguageUser(): LiveData<String> {
        return useCase.getLanguageUser().asLiveData()
    }

    fun saveLanguageUser(language: String) {
        viewModelScope.launch {
            useCase.saveLanguageUser(language)
        }
    }
}