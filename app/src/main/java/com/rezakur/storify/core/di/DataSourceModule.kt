package com.rezakur.storify.core.di

import com.rezakur.storify.data.sources.remote.AuthRemoteDataSource
import com.rezakur.storify.data.sources.remote.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl):
            AuthRemoteDataSource
}