package com.example.demosample.domain.repo

import com.example.demosample.data.local.entity.UsersProfileEntity
import com.example.demosample.data.remote.dto.UsersProfilesDto
import com.example.demosample.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersProfileRepo {
    fun getMyChoiceUsersFromDb(): Flow<List<UsersProfileEntity>>
    fun getAlUserFromDb(pageNo: Int): Flow<List<UsersProfileEntity>>
    fun updateInvitation(id: String,status: String): Flow<Int>
    fun getUserFromServer(pageNo: Int): Flow<List<UsersProfilesDto>>
}