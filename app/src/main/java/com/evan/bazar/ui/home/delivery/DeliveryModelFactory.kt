package com.evan.bazar.ui.home.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.supplier.SupplierSourceFactory
import com.evan.bazar.ui.home.supplier.SupplierViewModel

class DeliveryModelFactory (
    private val repository: HomeRepository, private val sourceFactory: DeliverySourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeliveryViewModel(repository,sourceFactory) as T
    }
}