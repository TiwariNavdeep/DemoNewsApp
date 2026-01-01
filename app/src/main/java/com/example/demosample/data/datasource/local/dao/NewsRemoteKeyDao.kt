package com.example.demosample.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demosample.data.datasource.local.entity.NewsRemoteKey

@Dao
interface NewsRemoteKeyDao {

    @Query("SELECT * FROM news_remote_keys WHERE category = :category")
    suspend fun remoteKeyByCategory(category: String): NewsRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: NewsRemoteKey)

    @Query("DELETE FROM news_remote_keys WHERE category = :category")
    suspend fun clear(category: String)
}