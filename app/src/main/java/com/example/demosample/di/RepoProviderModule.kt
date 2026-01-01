package com.example.demosample.di

import com.example.demosample.data.repository.NewsRepoImpl
import com.example.demosample.domain.repo.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoProviderModule{
    @Binds
    abstract fun bindNewsRepository(
        impl: NewsRepoImpl
    ): NewsRepository
}