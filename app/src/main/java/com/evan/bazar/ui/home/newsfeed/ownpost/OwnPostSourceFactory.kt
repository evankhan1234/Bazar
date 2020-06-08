package com.evan.bazar.ui.home.newsfeed.ownpost

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.Post
import com.evan.bazar.data.db.entities.Shop


class OwnPostSourceFactory  (private var dataSource: OwnPostDataSource) :
    DataSource.Factory<Int, Post>() {

    val mutableLiveData: MutableLiveData<OwnPostDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Post> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}