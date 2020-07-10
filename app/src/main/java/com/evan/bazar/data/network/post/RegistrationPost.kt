package com.evan.bazar.data.network.post

data class RegistrationPost (
    val Email: String?,
    val Password: String?,
    val OwnerMobileNumber: String?,
    val OwnerName: String?,
    val AgreementDate: String?,
    val OwnerAddress: String?,
    val Status: String?,
    val ShopTypeId: String?,
    val Picture: String?,
    val Created: String?,
    val Name: String?,
    val Address: String?,
    val LicenseNumber: String?,
    val Latitude: Double?,
    val Longitude: Double?
)