package com.evan.bazar.ui.home.delivery

interface IDeliveryPostListener {
    fun show(value:String)
    fun started()
    fun end()
}