package com.evan.bazar.ui.home.product

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.data.network.post.SearchCategoryPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines

import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class ProductViewModel  (
    val repository: HomeRepository,
    val alertListSourceFactory: ProductSourceFactory
) : ViewModel() {

    var listener:IProductListener?=null
    var searchPost: SearchCategoryPost?=null
    var listOfAlerts: LiveData<PagedList<Product>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<ProductDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            ProductDataSource::networkState
        )
    fun getProduct(header:String,keyword:String) {
        listener?.started()
        Coroutines.main {
            try {
                searchPost= SearchCategoryPost(keyword)
                val response = repository.getProductSearch(header,searchPost!!)
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