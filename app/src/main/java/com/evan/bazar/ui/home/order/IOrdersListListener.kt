package com.evan.bazar.ui.home.order

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.Orders

interface IOrdersListListener {
    fun order(data:MutableList<Orders>?)
    fun onStarted()
    fun onEnd()
}