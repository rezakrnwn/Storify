package com.rezakur.storify.core.extensions

import com.rezakur.storify.core.utils.Either

fun <L, R> Either<L, R>.getRight(): R? =
    when (this) {
        is Either.Right -> this.value
        is Either.Left -> null
    }

fun <L, R> Either<L, R>.getLeft(): L? =
    when (this) {
        is Either.Left -> this.value
        is Either.Right -> null
    }

val <L, R> Either<L, R>.isLeft: Boolean
    get() = this is Either.Left

val <L, R> Either<L, R>.isRight: Boolean
    get() = this is Either.Right

inline fun <L, R, R2> Either<L, R>.map(
    transform: (R) -> R2
): Either<L, R2> =
    when (this) {
        is Either.Left -> this
        is Either.Right -> Either.Right(transform(value))
    }

inline fun <L, R, R2> Either<L, R>.flatMap(
    transform: (R) -> Either<L, R2>
): Either<L, R2> =
    when (this) {
        is Either.Left -> this
        is Either.Right -> transform(value)
    }