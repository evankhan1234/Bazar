package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SystemList (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("ItemCode")
    var ItemCode: String?,
    @SerializedName("ShopType")
    var ShopType: Int?,
    @SerializedName("ItemName")
    var ItemName: String?,
    @SerializedName("ItemDescription")
    var ItemDescription: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("SalesPrice")
    var SalesPrice: Double?,
    @SerializedName("PurchasePrice")
    var PurchasePrice: Double?,
    @SerializedName("Vat")
    var Vat: Double?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("ItemCountry")
    var ItemCountry: String?,
    @SerializedName("ItemBrand")
    var ItemBrand: String?,
    @SerializedName("Stock")
    var Stock: Int?,
    @SerializedName("Discount")
    var Discount: Double?,
    @SerializedName("ShopUserId")
    var ShopUserId: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("CreatedBy")
    var CreatedBy: Int?,
    @SerializedName("Status")
    var Status: Int?
):Parcelable