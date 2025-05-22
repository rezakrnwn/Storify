package com.rezakur.storify.data.sources.remote

import com.rezakur.storify.data.sources.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StorifyApi {
    @POST("v1/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}