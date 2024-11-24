package com.demo.moviedemo.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.fasterxml.jackson.annotation.JsonInclude
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Immutable
@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovieDetail(
    @SerializedName("id")
    val id: Long,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("budget")
    val budget: String? = null,
    @SerializedName("genres")
    val genres: List<Genre>? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("revenue")
    val revenue: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null,
)

@Immutable
@Stable
@Serializable
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String = ""
)

@Immutable
@Stable
@JsonInclude(JsonInclude.Include.NON_NULL)
@Serializable
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String = "",
    @SerialName("iso_639_1")
    val iso6391: String = "",
    @SerializedName("name")
    val name: String = ""
)

@Immutable
@Stable
@Serializable
data class ProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?, // Nullable, as logo_path can be null
    @SerializedName("name")
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: String = ""
)

@Immutable
@Stable
@Serializable
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("name")
    val name: String = ""
)