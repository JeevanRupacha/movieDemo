package com.demo.moviedemo.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.fasterxml.jackson.annotation.JsonInclude

@Stable
@Immutable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovieID(
    val id: Long,
    val adult: Boolean
)