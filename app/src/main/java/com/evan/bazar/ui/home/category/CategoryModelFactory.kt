package com.evan.bazar.ui.home.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.HomeViewModel

class CategoryModelFactory (
    private val repository: HomeRepository,private val sourceFactory: CategorySourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository,sourceFactory) as T
    }
}