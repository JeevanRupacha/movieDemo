package com.demo.moviedemo.core.utils

class LoadingState private constructor(val status: Status) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        val ERROR = LoadingState(Status.FAILED)
    }

    val isLoading = status == Status.RUNNING

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE
    }
}