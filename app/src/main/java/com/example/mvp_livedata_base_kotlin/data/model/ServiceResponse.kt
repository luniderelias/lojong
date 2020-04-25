package com.example.mvp_livedata_base_kotlin.data.model


sealed class ServiceResponse<out T> {
    object OK : ServiceResponse<Nothing>()
    class BODY<out T>(val value: T) : ServiceResponse<T>()
    object EMPTYBODY : ServiceResponse<Nothing>()
    class ERROR(val message: String = "") : ServiceResponse<Nothing>()
}

fun <T : Any, R : Any> ServiceResponse<T>.convert(function: (T) -> R) = when (this) {
    is ServiceResponse.BODY -> ServiceResponse.BODY(function(this.value))
    is ServiceResponse.EMPTYBODY -> this
    is ServiceResponse.OK -> this
    is ServiceResponse.ERROR -> this
}

fun <T : Any, R : Any> ServiceResponse<List<T>>.convertList(function: (T) -> R): ServiceResponse<List<R>> {
    return this.convert { it.map(function) }
}

fun <T> ServiceResponse<T>.whenever(
    isBody: (T) -> Unit = {},
    isEmptyBody: () -> Unit = {},
    isError: (String) -> Unit = {},
    isOk: () -> Unit = {}
) {
    when (this) {
        is ServiceResponse.BODY<T> -> isBody(this.value)
        is ServiceResponse.EMPTYBODY -> isEmptyBody()
        is ServiceResponse.ERROR -> isError(this.message)
        is ServiceResponse.OK -> isOk()
    }
}

fun <T> T.toServiceBody(): ServiceResponse.BODY<T> {
    return ServiceResponse.BODY(this)
}

fun <T> ServiceResponse<T>.valueOrNull(): T? {
    return when (this) {
        is ServiceResponse.BODY<T> -> this.value
        else -> null
    }
}

fun <T> ServiceResponse<T>.isSuccessful(): Boolean {
    return when (this) {
        is ServiceResponse.ERROR -> false
        else -> true
    }
}