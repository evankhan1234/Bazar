package com.evan.bazar.ui.home.purchase

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.ui.home.supplier.SupplierDataSource

class PurchaseSourceFactory (private var purchaseDataSource: PurchaseDataSource) :
    DataSource.Factory<Int, Purchase>() {

    val mutableLiveData: MutableLiveData<PurchaseDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Purchase> {
        mutableLiveData.postValue(purchaseDataSource)
        return purchaseDataSource
    }
}