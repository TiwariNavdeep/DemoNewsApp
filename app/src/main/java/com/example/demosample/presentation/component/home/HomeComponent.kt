package com.example.demosample.presentation.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.news.R
import com.example.demosample.presentation.theme.AppTypography
import com.example.demosample.presentation.theme.AppTypography.labelMedium10
import com.example.demosample.presentation.theme.AppTypography.labelMedium12
import com.example.demosample.presentation.theme.AppTypography.labelMedium16
import com.example.demosample.presentation.theme.LocalAppColors
import com.example.demosample.presentation.theme.MyApplicationDemoTheme

@Preview
@Composable
fun PreviewHomeTopBar(){
    MyApplicationDemoTheme {
        HomeTopBar(
            false
        ){}
    }
}
@Composable
fun HomeTopBar(
    isNetWorkAvailable: Boolean,
    onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = AppTypography.Typography.labelMedium16.copy(
                fontSize = 18.sp
            ),
            color = LocalAppColors.current.textPrimary,
            modifier = Modifier
                .align(Alignment.Center)
        )

        if(isNetWorkAvailable){
            IconButton(
                onClick = {
                    onSearchClick()
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Clear",
                    tint = LocalAppColors.current.textPrimary
                )
            }
        }else{
            Text(
                text = "Offline Mode",
                style = AppTypography.Typography.labelMedium10,
                color = LocalAppColors.current.error,
                modifier = Modifier
                    .background(LocalAppColors.current.error.copy(
                        .1f
                    ))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .align(Alignment.CenterEnd)
            )
        }

    }
}