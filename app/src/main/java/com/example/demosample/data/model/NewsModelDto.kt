package com.example.demosample.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val message: String,
    @SerializedName("articles")
    val news: List<NewsModelDto>,
)
data class NewsModelDto(
    val source : NewSourceDto,
    val id: String,
    val title: String,
    val url: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    )

data class NewSourceDto(
    val id: String,
    val name: String,
    @SerializedName("url")
    val url: String,
)


data class NewsResponseError(
    val message: String,
)

