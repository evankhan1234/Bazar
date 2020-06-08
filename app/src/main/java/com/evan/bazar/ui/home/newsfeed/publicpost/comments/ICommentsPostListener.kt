package com.evan.bazar.ui.home.newsfeed.publicpost.comments

interface ICommentsPostListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}