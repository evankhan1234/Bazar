package com.evan.bazar.ui.home.supplier

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.Supplier

interface ISupplierListener {
    fun show(data:MutableList<Supplier>)
    fun started()
    fun end()
    fun exit()
}