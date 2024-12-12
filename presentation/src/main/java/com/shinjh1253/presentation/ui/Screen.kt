package com.shinjh1253.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import com.shinjh1253.presentation.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class Screen(
    val route: String,
    val navArguments: ImmutableList<NamedNavArgument> = persistentListOf(),
) {

    data object Main : Screen(route = ROUTE_MAIN) {

        sealed class MainTab(
            route: String,
            @StringRes val titleResId: Int,
            @DrawableRes val iconResId: Int,
        ) : Screen(route, persistentListOf()) {

            data object Search : MainTab(
                route = ROUTE_SEARCH,
                titleResId = R.string.search,
                iconResId = R.drawable.ic_search
            )

            data object Bookmark : MainTab(
                route = ROUTE_BOOKMARK,
                titleResId = R.string.bookmark,
                iconResId = R.drawable.ic_bookmarks
            )
        }
    }

    companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_BOOKMARK = "bookmark"

        const val SEARCH_RESULT_KEYWORD = "SearchResultKeyword"
    }
}