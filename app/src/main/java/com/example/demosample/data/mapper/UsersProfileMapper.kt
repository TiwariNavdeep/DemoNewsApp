package com.example.demosample.data.mapper

import com.example.demosample.data.local.entity.UsersProfileEntity
import com.example.demosample.data.remote.dto.UsersProfilesDto
import com.example.demosample.domain.model.UserModel

object UsersProfileMapper {

    fun dtoToDomain(userDto: UsersProfilesDto): UserModel = UserModel(
        id = userDto.email?:"",
        name = userDto.name?.title+" "+userDto.name?.first+" "+userDto.name?.last,
        profilePicture = userDto.picture?.large?:"",
        profilePictureBlur = userDto.picture?.thumbnail?:"",
        age = "${userDto.dob?.age?:10}",
        address = userDto.location?.state+", "+userDto.location?.country
    )
    fun entityToDomain(userDto: UsersProfileEntity): UserModel = UserModel(
        id = userDto.id?:"",
        name = userDto.name,
        profilePicture = userDto.profilePicture,
        profilePictureBlur = userDto.profilePictureBlur,
        age = userDto.age,
        address = userDto.address,
        invitationStatus = userDto.invitationStatus,
    )
    fun dtoToEntity(userDto: UsersProfilesDto): UsersProfileEntity = UsersProfileEntity(
        id = userDto.email?:"",
        name = userDto.name?.title+" "+userDto.name?.first+" "+userDto.name?.last,
        profilePicture = userDto.picture?.large?:"",
        profilePictureBlur = userDto.picture?.thumbnail?:"",
        age = "${userDto.dob?.age?:10}",
        address = userDto.location?.state+", "+userDto.location?.country,
        modifiedDate = System.currentTimeMillis()
    )
}