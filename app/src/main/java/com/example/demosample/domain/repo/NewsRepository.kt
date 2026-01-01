package com.example.demosample.domain.repo

import androidx.paging.PagingData
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsByCategory(
        category: NewsCategory
    ): Flow<PagingData<NewsModel>>

    fun searchNews(
        query: String
    ): Flow<PagingData<NewsModel>>

}