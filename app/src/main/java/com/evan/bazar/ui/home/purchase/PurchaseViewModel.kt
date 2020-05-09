package com.evan.bazar.ui.home.purchase

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.data.repositories.HomeRepository

import com.evan.bazar.util.NetworkState

class PurchaseViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: PurchaseSourceFactory
) : ViewModel() {


    var listOfAlerts: LiveData<PagedList<Purchase>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Purchase>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Purchase>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<PurchaseDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            PurchaseDataSource::networkState
        )


}