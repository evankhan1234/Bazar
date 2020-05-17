package com.evan.bazar.ui.home.supplier

interface ICreateSupplierListener {
    fun show(value:String)
    fun failure(value:String)
    fun started()
    fun end()
}