package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Purchase (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("ProductName")
    var ProductName: String?,
    @SerializedName("ProductDetails")
    var ProductDetails: String?,
    @SerializedName("PurchaseNo")
    var PurchaseNo: String?,
    @SerializedName("PurchaseDate")
    var PurchaseDate: String?,
    @SerializedName("Stock")
    var Stock: String?,
    @SerializedName("Item")
    var Item: Int?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("Rate")
    var Rate: Double?,
    @SerializedName("Discount")
    var Discount: Double?,
    @SerializedName("Total")
    var Total: Double?,
    @SerializedName("GrandTotal")
    var GrandTotal: Double?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?,
    @SerializedName("Status")
    var Status: Int?
): Parcelable