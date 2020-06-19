package com.evan.bazar.ui.home.chat

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.ChatList

interface IChatListener {
    fun show(data:MutableList<ChatList>)
    fun started()
    fun end()
    fun exit()
}