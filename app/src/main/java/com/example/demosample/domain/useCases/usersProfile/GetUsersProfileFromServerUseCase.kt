package com.example.demosample.domain.useCases.usersProfile

import com.example.demosample.data.mapper.UsersProfileMapper
import com.example.demosample.domain.model.UserModel
import com.example.demosample.domain.repo.UsersProfileRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersProfileFromServerUseCase @Inject constructor(
    val repo: UsersProfileRepo
) {
    operator fun invoke(page: Int=1): Flow<List<UserModel>>{
       return repo.getUserFromServer(page)
           .map {list->
               list.map { UsersProfileMapper.dtoToDomain(it) }
           }
    }
}