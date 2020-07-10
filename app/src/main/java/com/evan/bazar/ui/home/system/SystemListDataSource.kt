package com.evan.bazar.ui.home.system

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evan.bazar.data.db.entities.SystemList
import com.evan.bazar.data.network.post.SystemPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.*
import com.google.gson.Gson

class SystemListDataSource (val context: Context, val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, SystemList>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var limitPost: SystemPost? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SystemList>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)

            var id:String?=""
            id= SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_TYPE_ID)!!
            try {
                networkState.postValue(NetworkState.LOADING)
                limitPost = SystemPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_TYPE_ID)!!.toInt(),10, 1)
                val response = alertRepository.getSystemList(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,limitPost!!)
                Log.e("response","response"+ Gson().toJson(response))
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        networkState.postValue(NetworkState.DONE)
                        val nextPageKey = 2
                        callback.onResult(response.data!!, null, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )
                    }
                }
            } catch (e: ApiException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SystemList>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                limitPost = SystemPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_TYPE_ID)!!.toInt(),10, params.key)
                val response =
                    alertRepository.getSystemList(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,limitPost!!)
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        val nextPageKey =
                            params.key + 1
                        networkState.postValue(NetworkState.DONE)
                        callback.onResult(response.data!!, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )

                    }
                }
            } catch (e: ApiException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SystemList>) {
    }


}