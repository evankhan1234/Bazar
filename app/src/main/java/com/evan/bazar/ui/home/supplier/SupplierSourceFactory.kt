package com.evan.bazar.ui.home.supplier

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.Supplier
import com.evan.bazar.ui.home.category.CategoryDataSource

class SupplierSourceFactory (private var supplierListDataSource: SupplierDataSource) :
    DataSource.Factory<Int, Supplier>() {

    val mutableLiveData: MutableLiveData<SupplierDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Supplier> {
        mutableLiveData.postValue(supplierListDataSource)
        return supplierListDataSource
    }
}