package com.ckatsidzira.presentation.custom.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ckatsidzira.BuildConfig
import com.ckatsidzira.R
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.util.MOCK_MOVIE

@Composable
fun CarouselCard(
    movie: MovieUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    val posterWidth = 140.dp
    val posterAspectRatio = 2f / 3f
    val posterHeight = posterWidth / posterAspectRatio
    val titleHeight = 40.dp // enough space for 2 lines

    Card(
        modifier = modifier
            .width(posterWidth)
            .height(posterHeight + titleHeight) // total fixed height
            .clickable { onClick(movie.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_elevation)
        )
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.IMAGES_BASE_URL + movie.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(posterHeight)
            )
            Text(
                text = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
@Preview
fun PreviewCarouselCard() {
    CarouselCard (
        movie = MOCK_MOVIE,
        onClick = { },
    )
}

