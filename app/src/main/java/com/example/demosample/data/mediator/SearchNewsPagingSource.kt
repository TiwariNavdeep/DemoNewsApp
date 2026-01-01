package com.example.demosample.data.mediator

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.demosample.data.datasource.remote.ApiService
import com.example.demosample.data.mapper.NewsMapper
import com.example.demosample.data.model.NewsResponseError
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.google.gson.Gson

class SearchNewsPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, NewsModel>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, NewsModel> {

        return try {
            val page = params.key ?: 1
            val response = apiService.searchNews(
                query = query,
                page = page
            )
            if (response.isSuccessful) {
                response.body()?.let { newsBody ->
                    val newsList = newsBody.news.map {
                        NewsMapper.dtoToDomain(
                            it,
                            NewsCategory.ALL
                        )
                    }
                    LoadResult.Page(
                        data = newsList,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (newsBody.news.isEmpty()) null else page + 1
                    )
                } ?: run {
                    LoadResult.Error(Exception("No news found"))
                }

            } else {
                // 👇 HERE you will get your error JSON
                val errorBody = response.errorBody()?.string()
                val newsResponseError = Gson()
                    .fromJson(errorBody, NewsResponseError::class.java)
                val message = newsResponseError.message
                LoadResult.Error(Exception(message))
            }
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.message))
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, NewsModel>
    ): Int? {
        return state.anchorPosition
    }
}
