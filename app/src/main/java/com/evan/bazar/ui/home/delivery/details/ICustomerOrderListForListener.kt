package com.evan.bazar.ui.home.delivery.details

import com.evan.bazar.data.db.entities.CustomerOrderList


interface ICustomerOrderListForListener {
    fun order(data:MutableList<CustomerOrderList>?)
    fun onStarted()
    fun onEnd()
}