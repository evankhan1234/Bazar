package com.evan.bazar.ui.home.delivery.details

import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.CustomerOrderDetails


interface ICustomerOrderListener {
    fun onShow(customerOrder: CustomerOrderDetails?)
    fun onEmpty()
}