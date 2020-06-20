package com.evan.bazar.ui.home.chat

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.ChatList

interface IChatViewListener {
    fun onUpdate(chatList: ChatList)

}