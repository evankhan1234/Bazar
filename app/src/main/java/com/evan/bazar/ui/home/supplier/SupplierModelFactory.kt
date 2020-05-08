package com.evan.bazar.ui.home.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.ui.home.category.CategoryViewModel

class SupplierModelFactory  (
    private val repository: HomeRepository, private val sourceFactory: SupplierSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SupplierViewModel(repository,sourceFactory) as T
    }
}