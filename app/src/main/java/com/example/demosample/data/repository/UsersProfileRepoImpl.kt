package com.example.demosample.data.repository

import com.example.demosample.data.local.dao.UsersProfileDao
import com.example.demosample.data.local.entity.UsersProfileEntity
import com.example.demosample.data.mapper.UsersProfileMapper
import com.example.demosample.data.remote.api.UsersProfileApiService
import com.example.demosample.data.remote.dto.UsersProfilesDto
import com.example.demosample.domain.model.UserModel
import com.example.demosample.domain.repo.UsersProfileRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UsersProfileRepoImpl @Inject constructor(
    val dao: UsersProfileDao,
    val apiService: UsersProfileApiService
) : UsersProfileRepo {

    override fun getMyChoiceUsersFromDb(): Flow<List<UsersProfileEntity>> {
        return dao.getMyChoiceUsers().flowOn(Dispatchers.IO)
    }

    override fun getAlUserFromDb(pageNo: Int): Flow<List<UsersProfileEntity>> {
        return flow {
            emit(dao.getAllUsers())
        }.flowOn(Dispatchers.IO)
    }

    override fun updateInvitation(
        id: String,
        status: String
    ): Flow<Int> {
        return flow{
            emit(dao.updateInvitationStatus(id,status, System.currentTimeMillis()) )
        }.flowOn(Dispatchers.IO)
    }

    /*
      * get random users from api & emit list of users
      * cast all users from dto Obj to entityObj and insert to local database
    * */
    override fun getUserFromServer(pageNo: Int): Flow<List<UsersProfilesDto>> {
        return flow {
            val res = apiService.getUsersProfile(10, page = pageNo)
            emit(res.results)
            dao.insertUsers(
                res.results.map {
                    UsersProfileMapper.dtoToEntity(it)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

}