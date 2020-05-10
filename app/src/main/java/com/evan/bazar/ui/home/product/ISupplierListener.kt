package com.evan.bazar.ui.home.product

import com.evan.bazar.data.db.entities.Supplier

interface ISupplierListener {
    fun supplier(shop:MutableList<Supplier>?)
}