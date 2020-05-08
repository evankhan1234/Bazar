package com.evan.bazar.ui.home.category

import com.evan.bazar.data.db.entities.CategoryType

interface ICategoryUpdateListener {
    fun onUpdate(category:CategoryType)
}