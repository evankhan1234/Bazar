package com.evan.bazar.ui.home.order

import com.evan.bazar.data.db.entities.Orders

interface IOrderUpdateListener {
    fun onUpdate(orders: Orders)
}