package com.demo.moviedemo.core.utils

object GetImageURL {
    operator fun invoke(
        path: String?,
        width: String = "w500"
    ): String {
        return Constants.IMDB_IMAGE_BASE_URL + "/$width" + path
    }
}