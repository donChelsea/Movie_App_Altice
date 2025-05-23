package com.ckatsidzira.presentation.custom

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
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
import androidx.compose.ui.unit.dp
import com.ckatsidzira.presentation.screen.details.DetailsUiAction

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
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp),
        content = {
            when (state) {
                FavoriteState.Toggling -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )

                FavoriteState.Favorited -> Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "",
                    tint = Color.White
                )

                FavoriteState.NotFavorited -> Icon(
                    imageVector = Icons.Outlined.BookmarkAdd,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}

@Immutable
sealed interface FavoriteState {
    data object Favorited : FavoriteState
    data object NotFavorited : FavoriteState
    data object Toggling : FavoriteState
}