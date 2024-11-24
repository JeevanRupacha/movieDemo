package com.demo.moviedemo.features.moveList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.moviedemo.R
import com.demo.moviedemo.core.theme.MovieDemoTheme

@Composable
fun MovieListTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.movie_list),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.W600
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MovieDemoTheme {
        MovieListTitle()
    }
}