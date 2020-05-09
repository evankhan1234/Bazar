package com.evan.bazar.ui.home.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.supplier.SupplierSourceFactory
import com.evan.bazar.ui.home.supplier.SupplierViewModel

class PurchaseModelFactory  (
    private val repository: HomeRepository, private val sourceFactory: PurchaseSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PurchaseViewModel(repository,sourceFactory) as T
    }
}