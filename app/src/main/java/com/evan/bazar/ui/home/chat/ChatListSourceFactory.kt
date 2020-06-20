package com.evan.bazar.ui.home.chat

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.ChatList

class ChatListSourceFactory  (private var eventListDataSource: ChatListDataSource) :
    DataSource.Factory<Int, ChatList>() {

    val mutableLiveData: MutableLiveData<ChatListDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, ChatList> {
        mutableLiveData.postValue(eventListDataSource)
        return eventListDataSource
    }
}