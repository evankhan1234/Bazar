package com.evan.bazar.ui.home.system

import com.evan.bazar.data.db.entities.SystemList

interface ISystemViewListener {
    fun onUpdate(system: SystemList)
}