package com.evan.bazar.ui.home.delivery

import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.data.db.entities.Supplier

interface IDeliveryUpdateListener {
    fun onUpdate(delivery: Delivery)
}