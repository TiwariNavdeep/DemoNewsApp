package com.example.demosample.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavGraphScreens(val route: String) {

    object Home : NavGraphScreens("home}")

    object Search : NavGraphScreens("search")

    object WebView : NavGraphScreens("webview/{url}") {
        fun createRoute(url: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "webview/$encodedUrl"
        }
    }
}