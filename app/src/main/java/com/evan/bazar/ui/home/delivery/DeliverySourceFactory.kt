package com.evan.bazar.ui.home.delivery

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.ui.home.supplier.SupplierDataSource

class DeliverySourceFactory (private var deliveryListDataSource: DeliveryDataSource) :
    DataSource.Factory<Int, Delivery>() {

    val mutableLiveData: MutableLiveData<DeliveryDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Delivery> {
        mutableLiveData.postValue(deliveryListDataSource)
        return deliveryListDataSource
    }
}