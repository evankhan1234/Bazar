package com.evan.bazar.ui.home.system

import com.evan.bazar.data.db.entities.SystemList

interface ISystemListener {
    fun show(data:MutableList<SystemList>)
    fun started()
    fun end()
    fun exit()
}