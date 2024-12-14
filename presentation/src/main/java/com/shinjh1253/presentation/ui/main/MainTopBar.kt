package com.shinjh1253.presentation.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shinjh1253.presentation.ui.Screen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navController: NavHostController,
    tabItems: List<Screen.MainTab>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val titleName = tabItems.find { it.route == currentDestination?.route }?.titleResId?.let {resId ->
        stringResource(resId)
    } ?: ""

    TopAppBar(
        title = { Text(text = titleName.uppercase(Locale.getDefault())) },
    )
}