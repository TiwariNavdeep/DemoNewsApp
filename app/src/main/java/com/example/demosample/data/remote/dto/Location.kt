package com.example.demosample.data.remote.dto

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("street") var street: Street? = Street(),
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("postcode") var postcode: String? = "",
    @SerializedName("coordinates") var coordinates: Coordinates? = Coordinates(),
    @SerializedName("timezone") var timezone: Timezone? = Timezone()
)

data class Timezone(
    @SerializedName("offset") var offset: String? = null,
    @SerializedName("description") var description: String? = null
)

data class Street(
    @SerializedName("number") var number: Int? = null,
    @SerializedName("name") var name: String? = null
)

data class Coordinates(
    @SerializedName("latitude") var latitude: String? = null,
    @SerializedName("longitude") var longitude: String? = null

)