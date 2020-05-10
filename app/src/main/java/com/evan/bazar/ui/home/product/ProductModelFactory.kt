package com.evan.bazar.ui.home.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.purchase.PurchaseSourceFactory
import com.evan.bazar.ui.home.purchase.PurchaseViewModel

class ProductModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ProductSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(repository,sourceFactory) as T
    }
}