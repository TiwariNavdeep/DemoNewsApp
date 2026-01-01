package com.example.demosample.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val source: String,
    val publishedAt: String,
    val url: String,
    val category: String,
    val createdAt: Long = System.currentTimeMillis(),
)
