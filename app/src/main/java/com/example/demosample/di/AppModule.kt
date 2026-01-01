package com.example.demosample.di

import android.content.Context
import androidx.room.Room
import com.example.demosample.data.datasource.local.AppDatabase
import com.example.demosample.data.datasource.remote.ApiService
import com.example.demosample.data.repository.NewsRepoImpl
import com.example.demosample.domain.useCase.GetNewsUseCase
import com.example.demosample.utils.constant.AppConstant
import com.example.demosample.utils.network.NetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase{
        val room = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "com.demo.news"
        )
        return room.build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {

        @Provides
        @Singleton
        fun provideNetworkObserver(@ApplicationContext context: Context): NetworkObserver {
            return NetworkObserver(context)
        }
    }
}