package com.evan.bazar.ui.home.purchase

import com.evan.bazar.data.db.entities.Unit

interface IUnitListener {
    fun unit(shop:MutableList<Unit>?)
}