package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerOrder (
    @SerializedName("Id")
    var Id: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("ProductId")
    var ProductId: Int?,
    @SerializedName("Item")
    var Item: Int?,
    @SerializedName("OrderId")
    var OrderId: Int?,
    @SerializedName("OrderStatus")
    var OrderStatus: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("CustomerId")
    var CustomerId: Int?,
    @SerializedName("Price")
    var Price: Double?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("created")
    var created: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?
): Parcelable