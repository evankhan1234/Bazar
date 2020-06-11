package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.Delivery
import com.evan.bazar.data.db.entities.LastFiveSales
import com.evan.bazar.data.db.entities.Store
import com.google.gson.annotations.SerializedName

class StoreCountResponses(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: Store?
)