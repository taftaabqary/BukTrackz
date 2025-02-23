package com.unsoed.buktrackz.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unsoed.buktrackz.di.Injection
import com.unsoed.buktrackz.domain.usecase.BookUseCase
import com.unsoed.buktrackz.ui.add.AddBookViewModel
import com.unsoed.buktrackz.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val useCase: BookUseCase): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(useCase) as T
        } else if(modelClass.isAssignableFrom(AddBookViewModel::class.java)) {
            AddBookViewModel(useCase) as T
        }  else {
            throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(Injection.provideBookUseCase(context))
                INSTANCE = instance
                instance
            }
        }
    }
}