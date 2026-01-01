package com.example.demosample.presentation.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.demosample.presentation.theme.AppTypography
import com.example.demosample.presentation.theme.AppTypography.labelRegular12
import com.example.demosample.presentation.theme.LocalAppColors
import org.w3c.dom.Text

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = LocalAppColors.current.textSecondary,
            style = AppTypography.Typography.labelRegular12,
            modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun LoadMoreErrorView(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalAppColors.current.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = LocalAppColors.current.textSecondary,
            style = AppTypography.Typography.labelRegular12,
            modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun PaginationLoader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading More...",
            color = LocalAppColors.current.textPrimary,
        )
    }
}


@Composable
fun NoMoreData(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(LocalAppColors.current.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = LocalAppColors.current.textPrimary,
        )
    }
}

