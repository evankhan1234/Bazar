package com.evan.bazar.ui.home.system

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.SystemList
import com.evan.bazar.data.network.post.SystemSearchPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class SystemListViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: SystemListSourceFactory
) : ViewModel() {

    var searchPost: SystemSearchPost?=null
    var listener: ISystemListener?=null
    var listOfAlerts: LiveData<PagedList<SystemList>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, SystemList>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, SystemList>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<SystemListDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            SystemListDataSource::networkState
        )

    fun getSystemList(header:String,keyword:String,shopTypeId:Int) {
        listener?.started()
        Coroutines.main {
            try {
                searchPost= SystemSearchPost(shopTypeId,keyword)
                val response = repository.getSystemSearchList(header,searchPost!!)
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