package com.ckatsidzira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.ckatsidzira.presentation.navigation.BottomNavigationBar
import com.ckatsidzira.presentation.navigation.Screen
import com.ckatsidzira.presentation.navigation.Screen.DetailArgs.ID
import com.ckatsidzira.presentation.navigation.TopNavigationBar
import com.ckatsidzira.presentation.screen.details.ui.DetailsScreen
import com.ckatsidzira.presentation.screen.favorites.ui.FavoritesScreen
import com.ckatsidzira.presentation.screen.home.ui.HomeScreen
import com.ckatsidzira.ui.theme.Movie_App_AlticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movie_App_AlticeTheme {
                val navController = rememberNavController()
                var title by remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopNavigationBar(
                            title = title,
                            showBackButton = title == Screen.Details.route,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    val graph =
                        navController.createGraph(startDestination = Screen.Home.route) {
                            composable(route = Screen.Home.route) {
                                HomeScreen(navController = navController)
                                title = Screen.Home.route
                            }
                            composable(route = Screen.Favorites.route) {
                                FavoritesScreen(navController = navController)
                                title = Screen.Favorites.route
                            }
                            composable(
                                route = Screen.Details.route + "/{$ID}",
                                arguments = listOf(
                                    navArgument(ID) {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                DetailsScreen()
                                title = Screen.Details.route
                            }
                        }

                    NavHost(
                        navController = navController,
                        graph = graph,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

