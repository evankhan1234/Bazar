package com.evan.bazar.ui.home.newsfeed.ownpost

import com.evan.bazar.data.db.entities.Post
import com.evan.bazar.data.db.entities.Shop

interface IOwnPostUpdatedListener {
    fun onUpdate(post: Post)
}