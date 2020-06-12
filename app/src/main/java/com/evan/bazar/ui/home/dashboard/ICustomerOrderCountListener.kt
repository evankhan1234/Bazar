package com.evan.bazar.ui.home.dashboard

import com.evan.bazar.data.db.entities.CustomerOrderCount

interface ICustomerOrderCountListener {
    fun onCount(customerOrderCount: CustomerOrderCount)
}