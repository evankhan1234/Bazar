package com.evan.bazar.ui.home.product

import com.evan.bazar.data.db.entities.CategoryType

interface ICategoryTypeListener {
    fun category(shop:MutableList<CategoryType>?)
}