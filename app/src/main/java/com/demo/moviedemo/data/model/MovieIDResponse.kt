package com.demo.moviedemo.data.model

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.collections.immutable.ImmutableList
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList

@Stable
data class MovieIDResponse(
    @SerializedName("results")
    val results: List<MovieID> = emptyList() // TODO make immutableList
)