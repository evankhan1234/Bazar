package com.evan.bazar.ui.home.newsfeed.publicpost.reply

import com.evan.bazar.data.db.entities.Comments
import com.evan.bazar.data.db.entities.Reply

interface IReplyListener {
    fun load(data:MutableList<Reply>?)
    fun onStarted()
    fun onEnd()
}