package com.example.demosample.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersProfileEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val age: String,
    val address: String,
    val profilePicture: String,
    val profilePictureBlur: String,
    val invitationStatus: String="",
    val modifiedDate: Long
)
