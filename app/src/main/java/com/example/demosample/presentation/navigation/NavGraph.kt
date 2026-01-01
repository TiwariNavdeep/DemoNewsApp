package com.example.demosample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.demosample.presentation.ui.home.HomeRoutes
import com.example.demosample.presentation.ui.search.SearchRoutes
import com.example.demosample.presentation.ui.webview.WebviewRoutes
import com.example.demosample.presentation.viewModel.HomeViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "main_graph"
    ) {
        mainNavGraph(navController)
    }
}


fun NavGraphBuilder.mainNavGraph(navController: NavHostController){
    navigation(
        startDestination = NavGraphScreens.Home.route, route = "main_graph"
    ){
        composable(
            NavGraphScreens.Home.route
        ) {backstackEntry->
            HomeRoutes(
                navController = navController)
        }

        composable(NavGraphScreens.Search.route) {
            SearchRoutes(
                navController
            )
        }

        composable(
            route = NavGraphScreens.WebView.route,
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebviewRoutes(
                navController,url
            )
        }

    }
}