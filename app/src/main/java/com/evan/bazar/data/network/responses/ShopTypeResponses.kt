package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.ShopType
import com.evan.bazar.data.db.entities.User
import com.google.gson.annotations.SerializedName

data class ShopTypeResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<ShopType>?

)