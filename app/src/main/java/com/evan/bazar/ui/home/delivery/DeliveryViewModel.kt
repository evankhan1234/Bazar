package com.evan.bazar.ui.home.delivery

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.data.network.post.DeliveryStatusPost
import com.evan.bazar.data.network.post.PushPost
import com.evan.bazar.data.network.post.SearchCategoryPost
import com.evan.bazar.data.network.post.TokenPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.dashboard.IPushListener
import com.evan.bazar.ui.home.supplier.ISupplierListener
import com.evan.bazar.ui.home.supplier.SupplierDataSource
import com.evan.bazar.ui.home.supplier.SupplierSourceFactory
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class DeliveryViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: DeliverySourceFactory
) : ViewModel() {
    var deliveryStatusPost: DeliveryStatusPost? = null
    var listOfAlerts: LiveData<PagedList<Delivery>>? = null
    var tokenPost:TokenPost?=null
    var pushListener: IPushListener?=null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Delivery>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Delivery>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<DeliveryDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            DeliveryDataSource::networkState
        )
    fun updateDeliveryStatus(header:String,deliveryId:Int,status:Int,details:String,charge:Double) {
        Coroutines.main {
            try {
                deliveryStatusPost= DeliveryStatusPost(deliveryId,status,details,charge)
                Log.e("response", "response" + Gson().toJson(deliveryStatusPost))
                val response = repository.updateDeliveryStatus(header,deliveryStatusPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getToken(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.getToken(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                pushListener?.onLoad(response.data!!)
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }
    fun sendPush(header:String,pushPost: PushPost) {
        Coroutines.main {
            try {
                Log.e("response", "response" + Gson().toJson(pushPost))
                val response = repository.sendPush(header,pushPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {
                Log.e("response", "response" + e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }
}