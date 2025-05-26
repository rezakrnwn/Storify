package com.rezakur.storify.core.di

import android.content.Context
import com.rezakur.storify.core.storage.PrefsManager
import com.rezakur.storify.core.storage.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): PrefsManager =
        DataStoreManager(context)
}