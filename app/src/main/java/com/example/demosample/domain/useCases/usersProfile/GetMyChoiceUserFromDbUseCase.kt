package com.example.demosample.domain.useCases.usersProfile

import com.example.demosample.data.mapper.UsersProfileMapper
import com.example.demosample.domain.model.UserModel
import com.example.demosample.domain.repo.UsersProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMyChoiceUserFromDbUseCase @Inject constructor (
    val repo: UsersProfileRepo
) {
    operator fun invoke(): Flow<List<UserModel>>{
       return repo.getMyChoiceUsersFromDb().map {list->
            list.map {
                UsersProfileMapper.entityToDomain(it)
            }
        }
    }
}