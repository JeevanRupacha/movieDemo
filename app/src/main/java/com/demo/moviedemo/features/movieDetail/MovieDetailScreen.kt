package com.demo.moviedemo.features.movieDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.moviedemo.R
import com.demo.moviedemo.core.ui.topAppBar.TopAppBar
import com.demo.moviedemo.core.utils.GetImageURL
import com.demo.moviedemo.data.model.Genre
import com.demo.moviedemo.data.model.ProductionCountry
import com.demo.moviedemo.data.model.SpokenLanguage

@Composable
fun MovieDetailScreen(
    state: MovieDetailState,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "",
                onBack = {}
            )
        }
    ) { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if(state.loadingState.isLoading){
                Loading()
            } else {
                MovieImage(state.movie?.posterPath)
                Spacer(modifier = Modifier.height(24.dp))
                MovieDetailCard(
                    title = state.movie?.title ?: "",
                    overview = state.movie?.overview ?: "",
                    genres = state.movie?.genres ?: emptyList(),
                    languages = state.movie?.spokenLanguages ?: emptyList(),
                    countries = state.movie?.productionCountries ?: emptyList(),
                    runtime = state.movie?.runtime ?: 0,
                    releaseDate = state.movie?.releaseDate ?: ""
                )
            }
        }
    }
}

@Composable
private fun MovieImage(
    imagePath: String?
) {
    Column(
        modifier = Modifier.clip(
            shape = RoundedCornerShape(
                bottomEnd = 42.dp,
                bottomStart = 42.dp
            )
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(GetImageURL(imagePath))
                .crossfade(true)
                .build()
            ,
            contentDescription = stringResource(R.string.movie_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailCard(
    title: String,
    overview: String,
    genres: List<Genre>,
    languages: List<SpokenLanguage>,
    countries: List<ProductionCountry>,
    runtime: Int,
    releaseDate: String,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(
            .4.dp,
            MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.mingcute_movie_line),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.movie_title_icon)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Text(
                text = overview,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.genre),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    genres.forEach { genre ->
                        SuggestionChip(
                            onClick = {},
                            enabled = true,
                            label = {
                                Text(
                                    text = genre.name,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.languages),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    languages.forEachIndexed { index, lang ->
                        Text(
                            text = lang.name + if(languages.lastIndex != index) ", " else "",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.countries),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    countries.filter { it.name.isNotBlank() }.forEachIndexed { index, country ->
                        Text(
                            text = country.name + if(languages.lastIndex != index) ", " else "",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SuggestionChip(
                    onClick = {},
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(R.string.released),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                SuggestionChip(
                    onClick = {},
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(R.string._s_mins, runtime),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                SuggestionChip(
                    onClick = {},
                    enabled = true,
                    label = {
                        Text(
                            text = releaseDate,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}