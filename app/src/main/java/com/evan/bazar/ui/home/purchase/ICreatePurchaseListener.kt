package com.evan.bazar.ui.home.purchase

interface ICreatePurchaseListener {
    fun show(value:String)
    fun failue(value:String)
    fun started()
    fun end()
}