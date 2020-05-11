package com.evan.bazar.ui.home.product

import com.evan.bazar.data.db.entities.Product

interface IProductUpdateListener {
    fun onUpdate(product: Product)
}