package com.evan.bazar.ui.home.product

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.ui.home.purchase.PurchaseDataSource

class ProductSourceFactory (private var productDataSource: ProductDataSource) :
    DataSource.Factory<Int, Product>() {

    val mutableLiveData: MutableLiveData<ProductDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Product> {
        mutableLiveData.postValue(productDataSource)
        return productDataSource
    }
}