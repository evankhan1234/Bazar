package com.evan.bazar.ui.home.purchase

import com.evan.bazar.data.db.entities.Purchase
import com.evan.bazar.data.db.entities.Supplier

interface IPurchaseListener {
    fun show(data:MutableList<Purchase>)
    fun started()
    fun end()
    fun exit()
}