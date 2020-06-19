package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.CategoryType
import com.evan.bazar.data.db.entities.ChatList
import com.google.gson.annotations.SerializedName

class ChatListResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<ChatList>?

)