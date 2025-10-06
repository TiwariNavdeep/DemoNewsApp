package com.example.demosample.data.remote.api

import com.example.demosample.data.remote.dto.UsersProfilesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersProfileApiService {

    @GET("api/")
    suspend fun getUsersProfile(
        @Query("results") results: Int,
        @Query("page") page: Int
    ): UsersProfilesResponse
}
