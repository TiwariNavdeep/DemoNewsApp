package com.example.demosample.domain.useCase

import androidx.paging.PagingData
import com.example.demosample.data.repository.NewsRepoImpl
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.domain.repo.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
   private val repo: NewsRepository
) {
    operator fun invoke(
        category: NewsCategory
    ): Flow<PagingData<NewsModel>> {
        return repo.getNewsByCategory(category)
    }
}