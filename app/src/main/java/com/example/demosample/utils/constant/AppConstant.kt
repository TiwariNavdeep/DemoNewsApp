package com.example.demosample.utils.constant

import com.example.demosample.domain.enums.NewsCategory

object AppConstant {
   // const val BASE_URL = "https://gnews.io/api/v4/"
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "7084efb5618c47299529f109d2f3d2c4"
    //const val API_KEY = "5c7f56498555473f98a8da4bebbd268f"
    const val MAX_NEWS_ARTICLE = 10
    val newsCategories = listOf(
        NewsCategory.ALL,
        NewsCategory.SPORTS,
        NewsCategory.ENTERTAINMENT,
        NewsCategory.TECHNOLOGY,
        NewsCategory.BUSINESS,
        NewsCategory.HEALTH,
    )
}