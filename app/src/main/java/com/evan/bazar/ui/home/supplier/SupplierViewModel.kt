package com.evan.bazar.ui.home.supplier

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.CategoryDataSource
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.util.NetworkState

class SupplierViewModel(
    val repository: HomeRepository,
    val alertListSourceFactory: SupplierSourceFactory
) : ViewModel() {


    var listOfAlerts: LiveData<PagedList<Supplier>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Supplier>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Supplier>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<SupplierDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            SupplierDataSource::networkState
        )


}