package com.unsoed.core.helper

import androidx.lifecycle.ViewModelProvider
import com.unsoed.core.domain.usecase.BookUseCase

class ViewModelFactory private constructor(private val useCase: com.unsoed.core.domain.usecase.BookUseCase): ViewModelProvider.NewInstanceFactory() {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            HomeViewModel(useCase) as T
//        } else if(modelClass.isAssignableFrom(AddBookViewModel::class.java)) {
//            AddBookViewModel(useCase) as T
//        } else if(modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
//            DetailBookViewModel(useCase) as T
//        }  else {
//            throw Throwable("Unknown ViewModel class: " + modelClass.name)
//        }
//
//    }
//
//    companion object {
//        private var INSTANCE: ViewModelFactory? = null
//
//        fun getInstance(context: Context): ViewModelFactory {
//            return INSTANCE ?: synchronized(this) {
//                val instance = ViewModelFactory(Injection.provideBookUseCase(context))
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}