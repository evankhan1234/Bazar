package com.evan.bazar.ui.home.category

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.CategoryType

class CategorySourceFactory (private var eventListDataSource: CategoryDataSource) :
    DataSource.Factory<Int, CategoryType>() {

    val mutableLiveData: MutableLiveData<CategoryDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, CategoryType> {
        mutableLiveData.postValue(eventListDataSource)
        return eventListDataSource
    }
}