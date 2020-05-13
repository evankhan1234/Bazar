package com.evan.bazar.ui.home.product

import com.evan.bazar.data.db.entities.Product
import com.evan.bazar.data.db.entities.Supplier

interface IProductListener {
    fun show(data:MutableList<Product>)
    fun started()
    fun end()
    fun exit()
}