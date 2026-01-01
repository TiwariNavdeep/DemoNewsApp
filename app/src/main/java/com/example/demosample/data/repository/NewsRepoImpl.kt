package com.example.demosample.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.demosample.data.datasource.local.AppDatabase
import com.example.demosample.data.datasource.remote.ApiService
import com.example.demosample.data.mapper.NewsMapper
import com.example.demosample.data.mediator.NewsRemoteMediator
import com.example.demosample.data.mediator.SearchNewsPagingSource
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.domain.repo.NewsRepository
import com.example.demosample.utils.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(
   private val apiService: ApiService,
   private val database: AppDatabase
): NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNewsByCategory(
        category: NewsCategory
    ): Flow<PagingData<NewsModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = AppConstant.MAX_NEWS_ARTICLE,
                initialLoadSize = AppConstant.MAX_NEWS_ARTICLE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            remoteMediator = NewsRemoteMediator(
                apiService,
                database,
                category
            ),
            pagingSourceFactory = {
                database.newsDao().pagingSource(category.value)
            }
        ).flow.map { pagingData ->
            val data = pagingData.map {
                NewsMapper.entityToDomain(it)
            }
            data
        }
    }


    override fun searchNews(query: String): Flow<PagingData<NewsModel>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(apiService, query)
            }
        ).flow
    }
}