package com.example.demosample.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_remote_keys")
data class NewsRemoteKey(
    @PrimaryKey val category: String,
    val nextPage: Int?
)