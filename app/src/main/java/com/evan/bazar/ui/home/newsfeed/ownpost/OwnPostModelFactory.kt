package com.evan.bazar.ui.home.newsfeed.ownpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository

class OwnPostModelFactory (
    private val repository: HomeRepository, private val sourceFactory: OwnPostSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OwnPostViewModel(repository,sourceFactory) as T
    }
}