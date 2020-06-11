package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.LastFiveSales
import com.evan.bazar.data.db.entities.ShopUser
import com.google.gson.annotations.SerializedName

class LastFiveSalesCountResponses(
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<LastFiveSales>?
)