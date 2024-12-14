package com.shinjh1253.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shinjh1253.presentation.ui.Screen
import com.shinjh1253.presentation.ui.bookmark.BookmarkRoute
import com.shinjh1253.presentation.ui.search.SearchRoute
import kotlinx.coroutines.launch

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    tabItems: List<Screen.MainTab> = Screen.mainTabItems
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainTopBar(
                navController = navHostController,
                tabItems = tabItems
            )
        },
        bottomBar = {
            MainBottomNavigation(
                navController = navHostController,
                tabItems = tabItems
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavHost(
                mainNavController = navHostController,
                startDestination = Screen.MainTab.Search.route,
                showSnackBar = { message, duration ->
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = duration
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {

    NavHost(
        modifier = modifier,
        navController = mainNavController,
        startDestination = startDestination
    ) {
        composable(route = Screen.MainTab.Search.route) {
            SearchRoute(
                showSnackBar = showSnackBar
            )
        }

        composable(route = Screen.MainTab.Bookmark.route) {
            BookmarkRoute(
                showSnackBar = showSnackBar
            )
        }
    }
}