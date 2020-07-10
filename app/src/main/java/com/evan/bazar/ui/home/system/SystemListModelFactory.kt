package com.evan.bazar.ui.home.system

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.supplier.SupplierSourceFactory
import com.evan.bazar.ui.home.supplier.SupplierViewModel

class SystemListModelFactory (
    private val repository: HomeRepository, private val sourceFactory: SystemListSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SystemListViewModel(repository,sourceFactory) as T
    }
}