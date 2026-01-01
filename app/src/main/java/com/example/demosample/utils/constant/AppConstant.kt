package com.example.demosample.utils.constant

import com.example.demosample.domain.enums.NewsCategory

object AppConstant {
   // const val BASE_URL = "https://gnews.io/api/v4/"
    const val BASE_URL = "https://newsapi.org/v2/"
    //const val API_KEY = "4cd40f743bf7112b4991158b6768fd39"//"897c31c57be551dbced73271e90c1ee2"
    const val API_KEY = "5c7f56498555473f98a8da4bebbd268f"//"897c31c57be551dbced73271e90c1ee2"
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