package com.example.demosample.di

import android.content.Context
import android.os.Build
import androidx.room.Room
import com.example.demosample.BuildConfig
import com.example.demosample.data.local.dao.UsersProfileDao
import com.example.demosample.data.local.database.AppDataBase
import com.example.demosample.data.remote.api.UsersProfileApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    fun provideApiService(
        client: OkHttpClient
    ): UsersProfileApiService =
        Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UsersProfileApiService::class.java)


    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        val room = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "matchmate.db"
        )
        return room.build()
    }

    @Provides
    @Singleton
    fun provideUsersProfileDaoDao(appDataBase: AppDataBase): UsersProfileDao{
        return appDataBase.usersProfileDao
    }

}