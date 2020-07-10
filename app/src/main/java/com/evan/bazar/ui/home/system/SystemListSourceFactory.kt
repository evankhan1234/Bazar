package com.evan.bazar.ui.home.system

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.SystemList

class SystemListSourceFactory (private var systemListDataSource: SystemListDataSource) :
    DataSource.Factory<Int, SystemList>() {

    val mutableLiveData: MutableLiveData<SystemListDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, SystemList> {
        mutableLiveData.postValue(systemListDataSource)
        return systemListDataSource
    }
}