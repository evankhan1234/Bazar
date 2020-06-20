package com.evan.bazar.ui.home.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.ui.home.category.CategoryViewModel

class ChatListModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ChatListSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatListViewModel(repository,sourceFactory) as T
    }
}