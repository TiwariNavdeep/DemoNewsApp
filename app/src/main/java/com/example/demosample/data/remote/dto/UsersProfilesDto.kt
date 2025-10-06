package com.example.demosample.data.remote.dto

import com.example.example.Login
import com.example.example.Registered
import com.google.gson.annotations.SerializedName


class UsersProfilesResponse(
    @SerializedName("results") var results: ArrayList<UsersProfilesDto> = arrayListOf(),
    @SerializedName("info") var info: UsersProfilesResponseInfo? = UsersProfilesResponseInfo()
)

data class UsersProfilesResponseInfo(
    @SerializedName("seed") var seed: String? = null,
    @SerializedName("results") var results: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("version") var version: String? = null
)

class UsersProfilesDto(
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("name") var name: Name? = Name(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("email") var email: String? = null,
    @SerializedName("login") var login: Login? = Login(),
    @SerializedName("dob") var dob: Dob? = Dob(),
    @SerializedName("registered") var registered: Registered? = Registered(),
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("cell") var cell: String? = null,
    @SerializedName("id") var id: Id? = Id(),
    @SerializedName("picture") var picture: Picture? = Picture(),
    @SerializedName("nat") var nat: String? = null
)

data class Id(
    @SerializedName("name") var name: String? = null,
    @SerializedName("value") var value: String? = null
)

data class Name(
    @SerializedName("title") var title: String? = null,
    @SerializedName("first") var first: String? = null,
    @SerializedName("last") var last: String? = null
)

data class Dob(
    @SerializedName("date") var date: String? = null,
    @SerializedName("age") var age: Int? = null
)

data class Picture(
    @SerializedName("large") var large: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null

)


