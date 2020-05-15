package com.evan.bazar.ui.home.delivery

interface IDeleteListener {
    fun onStartedView()
    fun onEndView()
    fun onSuccess(message:String)
    fun onFailure(message:String)
}