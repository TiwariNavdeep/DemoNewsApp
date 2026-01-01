package com.example.demosample.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demosample.data.datasource.local.entity.NewsEntity

@Dao
interface NewsFeedDao {

    @Query("SELECT * FROM news WHERE category = :category ORDER BY createdAt ASC")
    fun pagingSource(
        category: String
    ): PagingSource<Int, NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news WHERE category = :category")
    suspend fun clearCategory(category: String)

    @Query("SELECT COUNT(*) FROM news WHERE category=:category")
    suspend fun count(category: String): Int
}