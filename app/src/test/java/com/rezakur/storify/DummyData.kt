package com.rezakur.storify

import com.rezakur.storify.data.sources.remote.response.LoginResponse
import com.rezakur.storify.data.sources.remote.response.RegisterResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object DummyData {
    fun loginResponseSuccess() = LoginResponse(
        error = false,
        message = "successfully login",
        loginResult = LoginResponse.LoginResult(
            userId = "2311123",
            name = "Reza K.",
            token = "wk3211nclld21n5mm5ll121,se",
        )
    )

    fun loginResponseUnauthorized() =
        HttpException(
            Response.error<String>(
                401,
                "{'message':'email or password incorrect'}".toResponseBody()
            )
        )

    fun registerResponseSuccess() = RegisterResponse(
        error = false,
        message = "User Created",
    )

    fun registerResponseFailed() = HttpException(
        Response.error<String>(
            400,
            "{\"error\": true,\"message\": \"Email is already taken\"}".toResponseBody()
        )
    )
}