package com.evan.bazar.ui.home.newsfeed.publicpost

import com.evan.bazar.data.db.entities.Post

interface IPublicPostUpdateListener {
    fun onUpdate(post: Post)
}