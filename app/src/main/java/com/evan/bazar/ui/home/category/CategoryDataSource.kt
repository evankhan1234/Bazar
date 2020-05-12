package com.evan.bazar.ui.home.category

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.repositories.HomeRepository
import androidx.paging.PageKeyedDataSource
import com.evan.bazar.data.network.post.AuthPost
import com.evan.bazar.data.network.post.CategoryPost
import com.evan.bazar.util.*


class CategoryDataSource (val context: Context,val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, CategoryType>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var categoryPost: CategoryPost? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CategoryType>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)

            try {
                networkState.postValue(NetworkState.LOADING)
                categoryPost = CategoryPost(10, 1)
                val response = alertRepository.getCategoryTypePagination(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,categoryPost!!)
                Log.e("response","response"+response)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CategoryType>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                categoryPost = CategoryPost(10, params.key)
                val response =
                    alertRepository.getCategoryTypePagination(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,categoryPost!!)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CategoryType>) {
    }


}