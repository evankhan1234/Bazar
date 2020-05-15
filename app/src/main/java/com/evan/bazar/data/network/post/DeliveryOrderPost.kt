package com.evan.bazar.data.network.post

import com.google.gson.annotations.SerializedName

data class DeliveryOrderPost (
    @SerializedName("CustomerId")
    val CustomerId: Int?,
    @SerializedName("OrderId")
    val OrderId: Int?,
    @SerializedName("Discount")
    val Discount: Double?,
    @SerializedName("GrandTotal")
    val GrandTotal: Double?,
    @SerializedName("PaidAmount")
    val PaidAmount: Double?,
    @SerializedName("DueAmount")
    val DueAmount: Double?,
    @SerializedName("Total")
    val Total: Double?,
    @SerializedName("Status")
    val Status: Int?,
    @SerializedName("InvoiceNumber")
    val InvoiceNumber: String?,
    @SerializedName("Created")
    val Created: String?,
    @SerializedName("OrderDetails")
    val OrderDetails: String?,
    @SerializedName("DeliveryCharge")
    val DeliveryCharge: Double?
    )