package com.evan.bazar.ui.home.category

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.network.post.SearchCategoryPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class CategoryViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: CategorySourceFactory
) : ViewModel() {
    var categoryListener: ICategoryListener?=null

    var listOfAlerts: LiveData<PagedList<CategoryType>>? = null
    var searchPost: SearchCategoryPost?=null
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

    fun getCategoryType(header:String,keyword:String) {
        categoryListener?.started()
        Coroutines.main {
            try {
                searchPost= SearchCategoryPost(keyword)
                val response = repository.getCategorySearchType(header,searchPost!!)
                categoryListener?.show(response?.data!!)
                categoryListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                categoryListener?.end()
            } catch (e: NoInternetException) {
                categoryListener?.end()
            }
        }

    }
}