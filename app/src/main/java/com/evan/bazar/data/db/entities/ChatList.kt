package com.evan.bazar.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChatList  (
    @SerializedName("Id")
    var Id: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("FirebaseId")
    var FirebaseId: String?,
    @SerializedName("CustomerId")
    var CustomerId: String?

): Parcelable