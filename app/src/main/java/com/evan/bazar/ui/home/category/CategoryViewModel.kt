package com.evan.bazar.ui.home.category

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.NetworkState

class CategoryViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: CategorySourceFactory
) : ViewModel() {


    var listOfAlerts: LiveData<PagedList<CategoryType>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, CategoryType>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, CategoryType>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<CategoryDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            CategoryDataSource::networkState
        )

    
}