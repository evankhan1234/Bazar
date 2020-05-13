package com.evan.bazar.ui.home.settings

import com.evan.bazar.data.db.entities.ShopUser

interface IShopUserListener {
    fun show(shopUser: ShopUser?)
    fun onStarted()
    fun onEnd()
}