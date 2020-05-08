package com.evan.bazar.ui.home.supplier

import com.evan.bazar.data.db.entities.Supplier

interface ISupplierUpdateListener {
    fun onUpdate(supplier:Supplier)
}