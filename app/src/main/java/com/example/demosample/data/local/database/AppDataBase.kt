package com.example.demosample.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demosample.data.local.dao.UsersProfileDao
import com.example.demosample.data.local.entity.UsersProfileEntity

@Database(
    entities = [UsersProfileEntity::class],
    version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract val usersProfileDao:UsersProfileDao

}