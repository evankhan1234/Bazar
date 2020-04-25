package com.evan.bazar.ui.interfaces

import com.evan.bazar.data.db.entities.ShopType

interface ShopTypeInterface {
    fun shopType(shop:MutableList<ShopType>?)
}