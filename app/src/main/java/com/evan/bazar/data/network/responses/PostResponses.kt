package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.Notice
import com.evan.bazar.data.db.entities.Post
import com.google.gson.annotations.SerializedName

class PostResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Post>?
)