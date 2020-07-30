package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.CustomerOrderDetails
import com.google.gson.annotations.SerializedName

class CustomerDeliveryResponses (
    @SerializedName("success")
    var success : Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("data")
    var data: CustomerOrderDetails?
)