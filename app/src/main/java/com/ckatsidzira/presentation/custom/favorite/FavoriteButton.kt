package com.ckatsidzira.presentation.custom.favorite

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.ckatsidzira.R

@Composable
fun FavoriteButton(
    state: FavoriteState,
    modifier: Modifier = Modifier,
    onFavoriteClicked: () -> Unit,
) {
    val favoriteUpdate = remember { { onFavoriteClicked() } }

    SmallFloatingActionButton(
        modifier = modifier,
        onClick = { favoriteUpdate() },
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = dimensionResource(R.dimen.padding_10)),
        content = {
            when (state) {
                FavoriteState.Toggling -> CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_24)),
                    strokeWidth = dimensionResource(R.dimen.border_2),
                    color = Color.White
                )

                FavoriteState.Favorite -> Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "",
                    tint = Color.White
                )

                FavoriteState.NotFavorite -> Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}

@Immutable
sealed interface FavoriteState {
    data object Favorite : FavoriteState
    data object NotFavorite : FavoriteState
    data object Toggling : FavoriteState
}