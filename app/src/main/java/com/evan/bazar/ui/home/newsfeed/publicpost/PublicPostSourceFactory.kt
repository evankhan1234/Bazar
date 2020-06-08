package com.evan.bazar.ui.home.newsfeed.publicpost

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.Post
import com.evan.bazar.data.db.entities.Shop


class PublicPostSourceFactory  (private var dataSource: PublicPostDataSource) :
    DataSource.Factory<Int, Post>() {

    val mutableLiveData: MutableLiveData<PublicPostDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Post> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}