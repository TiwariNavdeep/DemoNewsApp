package com.example.demosample.data.datasource.remote

import com.example.demosample.data.model.NewsResponse
import com.example.demosample.utils.constant.AppConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/latest")
    suspend fun getNewList(): NewsResponse

    //@GET("top-headlines/")
    @GET("top-headlines/")
    suspend fun getCategoryNews(
        @Query("category") category: String = "sports",
        @Query("page") page: Int,
        @Query("lang") lang: String = "en",
        @Query("pageSize") max: Int = AppConstant.MAX_NEWS_ARTICLE,
        @Query("apikey") apiKey: String= AppConstant.API_KEY
    ): Response<NewsResponse>

    @GET("everything/")
    suspend fun searchNews(
        @Query("q") query: String ,
        @Query("page") page: Int,
        @Query("lang") lang: String = "en",
        @Query("pageSize") max: Int = AppConstant.MAX_NEWS_ARTICLE,
        @Query("apikey") apiKey: String= AppConstant.API_KEY
    ): Response<NewsResponse>
}