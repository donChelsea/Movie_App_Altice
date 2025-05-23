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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkButton(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookmarkClicked: () -> Unit,
) {
    SmallFloatingActionButton(
        modifier = modifier,
        onClick = onBookmarkClicked,
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp),
        content = {
            when (state) {
                BookmarkState.Toggling -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )

                BookmarkState.Bookmarked -> Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "",
                    tint = Color.White
                )

                BookmarkState.NotBookmarked -> Icon(
                    imageVector = Icons.Outlined.BookmarkAdd,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}

sealed interface BookmarkState {
    object Bookmarked : BookmarkState
    object NotBookmarked : BookmarkState
    object Toggling : BookmarkState
}