package com.evan.bazar.ui.home.supplier

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.data.network.post.SearchCategoryPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.CategoryDataSource
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class SupplierViewModel(
    val repository: HomeRepository,
    val alertListSourceFactory: SupplierSourceFactory
) : ViewModel() {

    var searchPost: SearchCategoryPost?=null
    var listener:ISupplierListener?=null
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

    fun getSupplier(header:String,keyword:String) {
        listener?.started()
        Coroutines.main {
            try {
                searchPost= SearchCategoryPost(keyword)
                val response = repository.getSupplierSearch(header,searchPost!!)
                listener?.show(response?.data!!)
                listener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                listener?.end()
                listener?.exit()
            } catch (e: NoInternetException) {
                listener?.end()
            }
        }

    }
}