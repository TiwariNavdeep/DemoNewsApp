package com.example.demosample.domain.useCases.usersProfile

import com.example.demosample.data.mapper.UsersProfileMapper
import com.example.demosample.domain.model.UserModel
import com.example.demosample.domain.repo.UsersProfileRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllUserFromDbUseCase @Inject constructor (
    val repo: UsersProfileRepo
) {
    operator fun invoke(): Flow<List<UserModel>>{
       return repo.getAlUserFromDb(1).map {list->
            list.map {
                UsersProfileMapper.entityToDomain(it)
            }
        }
    }
}