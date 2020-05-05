package com.evan.bazar.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    var Id: Int? = null,
    var OwnerName: String? = null,
    var Email: String? = null,
    var Password: String? = null,
    var AgreementDate: String? = null,
    var OwnerAddress: String? = null,
    var OwnerMobileNumber: String? = null,
    var Status: Int? = null,
    var ShopTypeId: Int? = null,
    var Picture: String? = null,
    var Created: String? = null
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}