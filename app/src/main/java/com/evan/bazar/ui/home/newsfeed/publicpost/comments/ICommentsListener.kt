package com.evan.bazar.ui.home.newsfeed.publicpost.comments

import com.evan.bazar.data.db.entities.Comments
import com.evan.bazar.data.db.entities.Product

interface ICommentsListener {
    fun load(data:MutableList<Comments>?)
    fun onStarted()
    fun onEnd()
}