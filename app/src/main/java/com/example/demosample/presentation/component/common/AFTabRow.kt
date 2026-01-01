package com.example.demosample.presentation.component.common

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyTabRowCategory(
    selectedTabIndex: Int,
    tabCount: Int,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    modifier: Modifier,
    tabs: @Composable () -> Unit
) {

    if(tabCount > 3){
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            edgePadding = 0.dp,
            indicator = {},
            divider = {
                //do nothing we did not need divider
            },
            modifier = modifier
        ) {
            tabs()
        }
    }else{
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = {},
            divider = {
                //do nothing we did not need divider
            },
            modifier = modifier
        ) {
            tabs()
        }
    }

}