package com.example.demosample.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.demosample.data.datasource.local.AppDatabase
import com.example.demosample.data.datasource.local.entity.NewsEntity
import com.example.demosample.data.datasource.local.entity.NewsRemoteKey
import com.example.demosample.data.datasource.remote.ApiService
import com.example.demosample.data.mapper.NewsMapper
import com.example.demosample.data.model.NewsResponseError
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.utils.constant.AppConstant
import com.google.gson.Gson


@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val category: NewsCategory
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> 1

            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )

            LoadType.APPEND -> {
                val remoteKey = database
                    .newsRemoteKeyDao()
                    .remoteKeyByCategory(category.value)

                remoteKey?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = apiService.getCategoryNews(
                category = category.value,
                page = page
            )
            if(response.isSuccessful){
                response.body()?.let {newsResponse ->
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.newsDao().clearCategory(category.value)
                            database.newsRemoteKeyDao().clear(category.value)
                        }
                        val nextPage =
                            if (newsResponse.news.size < AppConstant.MAX_NEWS_ARTICLE)
                                null
                            else
                                page + 1

                        database.newsRemoteKeyDao().insert(
                            NewsRemoteKey(
                                category = category.value,
                                nextPage = nextPage
                            )
                        )

                        database.newsDao().insertAll(
                            newsResponse.news.map {
                                NewsMapper.dtoToEntity(it, category)
                            }
                        )
                    }
                    MediatorResult.Success(
                        endOfPaginationReached = newsResponse.news.isEmpty()
                    )
                }?:run {
                    MediatorResult.Error(Exception("News Not Found!!"))
                }
            }else{
                // 👇 HERE you will get your error JSON
                val errorBody = response.errorBody()?.string()
                val newsResponseError = Gson()
                    .fromJson(errorBody, NewsResponseError::class.java)
                val message = newsResponseError.message
                MediatorResult.Error(Exception(message))
            }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
