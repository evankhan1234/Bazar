package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.Unit
import com.google.gson.annotations.SerializedName

data class UnitResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Unit>?
)