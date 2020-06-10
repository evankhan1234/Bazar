package com.evan.bazar.ui.home.dashboard

import com.evan.bazar.data.db.entities.Store

interface IStoreCountListener {
    fun onStore(store: Store)
}