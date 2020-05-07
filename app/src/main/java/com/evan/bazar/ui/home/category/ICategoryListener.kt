package com.evan.bazar.ui.home.category

import com.evan.bazar.data.db.entities.CategoryType

interface ICategoryListener {
    fun show(data:MutableList<CategoryType>)
    fun started()
    fun end()
}