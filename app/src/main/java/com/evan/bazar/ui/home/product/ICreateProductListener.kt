package com.evan.bazar.ui.home.product

interface ICreateProductListener {
    fun show(value:String)
    fun failure(value:String)
    fun started()
    fun end()
}