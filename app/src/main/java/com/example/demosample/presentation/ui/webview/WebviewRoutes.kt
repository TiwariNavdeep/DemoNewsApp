package com.example.demosample.presentation.ui.webview

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.demo.news.R
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.demosample.presentation.theme.LocalAppColors

@Composable
fun WebviewRoutes(
    navController: NavController,
    url: String
) {
    Scaffold {
        Box(
            modifier = Modifier.padding(it)
        ) {
            WebViewScreen(navController,url)
        }
    }
}

@Composable
fun WebViewScreen(
    navController: NavController,
    url: String
) {
    // Keep track of the WebView instance to handle back navigation
    var webView: WebView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Handle the back button behavior
    BackHandler(enabled = webView?.canGoBack() == true) {
        webView?.goBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(LocalAppColors.current.container)
                .padding(horizontal = 16.dp)
        ){
            IconButton(onClick = {
                navController.popBackStack()
            },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_clear_24),
                    contentDescription = "Clear",
                    tint = LocalAppColors.current.textPrimary
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        // Essential Configurations
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true

                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false // Hide loader when page finishes
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                isLoading = false
                                // Return false to let WebView handle the URL internally
                                return false
                            }
                        }

                        loadUrl(url)
                        webView = this
                    }
                },
                update = {
                    if (it.url != url) {
                        it.loadUrl(url)
                    }
                }
            )

            // Loading Overlay
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}