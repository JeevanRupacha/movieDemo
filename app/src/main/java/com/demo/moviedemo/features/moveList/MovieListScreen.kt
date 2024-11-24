package com.demo.moviedemo.features.moveList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.moviedemo.R
import com.demo.moviedemo.core.theme.MovieDemoTheme
import com.demo.moviedemo.core.utils.GetImageURL
import com.demo.moviedemo.core.utils.LocalDimension
import com.demo.moviedemo.core.utils.TestTag
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.features.moveList.components.MovieListTitle

@Composable
fun MovieListScreen(
    state: MovieListState,
    isLoading: Boolean,
    onClickMovie: (Long) -> Unit,
    loadMoreMovies: () -> Unit
) {
    val dimension = LocalDimension.current
    val screenXPadding = dimension.screenXPadding
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.canScrollForward }.collect{ canScrollForward ->
            if(!canScrollForward) loadMoreMovies()
        }
    }

    Scaffold { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()

        Column(
            modifier = Modifier
                .padding(
                    top = topPadding,
                    start = screenXPadding,
                    end = screenXPadding
                )
        ) {
            MovieListTitle()

            if(isLoading && state.movies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            } else {
                MovieList(
                    movies = state.movies,
                    state = gridState,
                    isNextLoading = isLoading,
                    onClickMovie = onClickMovie
                )
            }
        }
    }
}

@Composable
fun MovieList(
    movies: List<MovieDetail>,
    state: LazyGridState,
    isNextLoading: Boolean,
    onClickMovie: (id: Long) -> Unit
) {
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp),
        modifier = Modifier.testTag(TestTag.MOVIE_GRID)
    ) {
        items(
            items = movies,
            key = { it.id }
        ){ movie ->
            MovieItem(
                movie = movie,
                onClick = { onClickMovie(it) }
            )
        }

        if(isNextLoading) {
            item(
                span = { GridItemSpan(maxLineSpan)}
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieDetail,
    onClick: (id: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick(movie.id) }
            .semantics { contentDescription = TestTag.MOVIE_GRID} //testing
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(GetImageURL(movie.posterPath))
                    .placeholder(R.drawable.movieplace)
                    .error(R.drawable.movieplace)
                    .crossfade(true)
                    .build()
                ,
                contentDescription = stringResource(R.string.movie_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
            )
        }

        Text(
            text = movie.title ?: "",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 8.dp
                ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    MovieDemoTheme {
        MovieListScreen(
            state = MovieListState(),
            onClickMovie = {},
            loadMoreMovies = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMovieItem() {
    MovieDemoTheme {
        MovieItem(
            movie = MovieDetail(
                id = 2323,
                posterPath = "/2rE8Yc6V80qDrymOrixAARwtwXp.jpg"
            ),
            onClick = {}
        )
    }
}