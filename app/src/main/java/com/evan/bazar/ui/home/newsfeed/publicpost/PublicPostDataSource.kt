package com.evan.bazar.ui.home.newsfeed.publicpost

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evan.bazar.data.db.entities.Post
import com.evan.bazar.data.network.post.PublicForPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.util.*

class PublicPostDataSource (val context: Context, val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, Post>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var post: PublicForPost? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Post>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)

            try {
                networkState.postValue(NetworkState.LOADING)
                post = PublicForPost(10, 1,1)
                val response = alertRepository.getPublicPostPagination(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                post = PublicForPost(10, params.key,1)
                val response =
                    alertRepository.getPublicPostPagination(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
    }


}