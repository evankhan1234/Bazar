package com.evan.bazar.ui.home.purchase

import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.data.db.entities.Supplier

interface IPurchaseUpdateListener {
    fun onUpdate(purchase: Purchase)
}