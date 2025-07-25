package com.rezakur.storify.data.sources.remote.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?
)