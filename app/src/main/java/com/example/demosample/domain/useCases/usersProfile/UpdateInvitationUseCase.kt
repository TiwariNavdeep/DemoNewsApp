package com.example.demosample.domain.useCases.usersProfile

import com.example.demosample.domain.repo.UsersProfileRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateInvitationUseCase@Inject constructor(
    val repo: UsersProfileRepo
){
    operator fun invoke(id: String,status: String): Flow<Int>{
        return repo.updateInvitation(id,status)
    }
}