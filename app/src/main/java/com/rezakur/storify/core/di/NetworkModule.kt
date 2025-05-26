package com.rezakur.storify.core.di

import com.rezakur.storify.BuildConfig
import com.rezakur.storify.core.storage.PrefsManager
import com.rezakur.storify.core.constant.PrefConstants
import com.rezakur.storify.core.network.AuthInterceptor
import com.rezakur.storify.data.sources.remote.StorifyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }

    @Singleton
    @Provides
    fun provideAuthInterceptor(prefsManager: PrefsManager): AuthInterceptor = AuthInterceptor {
        runBlocking {
            prefsManager.getString(PrefConstants.TOKEN).firstOrNull()
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor?,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            httpLoggingInterceptor?.let { addInterceptor(httpLoggingInterceptor) }
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideStorifyApi(retrofit: Retrofit): StorifyApi = retrofit.create(StorifyApi::class.java)
}