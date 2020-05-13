package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopUser (
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("LicenseNumber")
    var LicenseNumber: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("AgreementDate")
    var AgreementDate: String?,
    @SerializedName("OwnerName")
    var OwnerName: String?,
    @SerializedName("OwnerAddress")
    var OwnerAddress: String?,
    @SerializedName("OwnerMobileNumber")
    var OwnerMobileNumber: String?,
    @SerializedName("ShopTypeId")
    var ShopTypeId: Int?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("Cover")
    var Cover: String?
    ): Parcelable