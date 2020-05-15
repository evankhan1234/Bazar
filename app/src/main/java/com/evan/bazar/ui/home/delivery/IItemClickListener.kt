package com.evan.bazar.ui.home.delivery

interface IItemClickListener {
    fun onClick(item:Int?,pricesProduct:Double?,orderId:Int?,type:Int?)
}