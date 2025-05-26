package com.rezakur.storify.core.di

import com.rezakur.storify.core.utils.DefaultValidators
import com.rezakur.storify.core.utils.Validators
import com.rezakur.storify.core.utils.dispatcher.DefaultDispatcherProvider
import com.rezakur.storify.core.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilityModule {

    @Singleton
    @Provides
    fun provideValidators(defaultValidators: DefaultValidators): Validators = defaultValidators

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}