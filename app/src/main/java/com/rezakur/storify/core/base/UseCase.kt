package com.rezakur.storify.core.base

interface UseCase<in Params, out Type> where Type: Any {
    suspend operator fun invoke(params: Params): Type

    class None
}