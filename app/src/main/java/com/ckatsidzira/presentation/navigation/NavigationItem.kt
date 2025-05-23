package com.ckatsidzira.presentation.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)