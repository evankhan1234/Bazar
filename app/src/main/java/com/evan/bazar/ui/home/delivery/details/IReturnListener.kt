package com.evan.bazar.ui.home.delivery.details

import com.evan.bazar.data.db.entities.CustomerOrderList

interface IReturnListener {
    fun onShow(customerOrderList: CustomerOrderList, reason:String)
}