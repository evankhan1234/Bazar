package com.evan.bazar.ui.home.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.bazar.data.db.entities.ChatList
import com.evan.bazar.data.network.post.SearchCategoryPost
import com.evan.bazar.data.repositories.HomeRepository
import com.evan.bazar.ui.home.category.CategoryDataSource
import com.evan.bazar.ui.home.category.CategorySourceFactory
import com.evan.bazar.ui.home.category.ICategoryListener
import com.evan.bazar.util.ApiException
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.NetworkState
import com.evan.bazar.util.NoInternetException
import com.google.gson.Gson

class ChatListViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: ChatListSourceFactory
) : ViewModel() {
    var chatListener: IChatListener?=null

    var listOfAlerts: LiveData<PagedList<ChatList>>? = null
    var searchPost: SearchCategoryPost?=null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, ChatList>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, ChatList>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<ChatListDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            ChatListDataSource::networkState
        )

    fun getCategoryType(header:String,keyword:String) {
        chatListener?.started()
        Coroutines.main {
            try {
                searchPost= SearchCategoryPost(keyword)
                val response = repository.getChatSearch(header,searchPost!!)
                chatListener?.show(response?.data!!)
                chatListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                chatListener?.end()
                chatListener?.exit()
            } catch (e: NoInternetException) {
                chatListener?.end()
            }
        }

    }
}