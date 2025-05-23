package com.ckatsidzira.presentation.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class Screen(val route: String) {
    data object Home: Screen("Home")
    data object Details: Screen("Details")
    data object Favorites: Screen("Favorites")

    object DetailArgs {
        const val ID = "id"
    }

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}