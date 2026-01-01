package com.example.demosample.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demosample.data.datasource.local.dao.NewsFeedDao
import com.example.demosample.data.datasource.local.dao.NewsRemoteKeyDao
import com.example.demosample.data.datasource.local.entity.NewsEntity
import com.example.demosample.data.datasource.local.entity.NewsRemoteKey

@Database(
    entities = [NewsEntity::class, NewsRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsFeedDao
    abstract fun newsRemoteKeyDao(): NewsRemoteKeyDao

}