package com.evan.bazar.ui.home.delivery

import com.evan.bazar.data.db.entities.CustomerOrder
import com.evan.bazar.data.db.entities.Orders

interface ICustomerOrderListListener {
    fun order(data:MutableList<CustomerOrder>?)
    fun onStarted()
    fun onEnd()
}