package com.demo.moviedemo.data.utils

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class Error<out T>(val errorMsg: String): ApiResult<T>()
    data class Ignored<out T>(val msg: String = ""): ApiResult<T>()
}