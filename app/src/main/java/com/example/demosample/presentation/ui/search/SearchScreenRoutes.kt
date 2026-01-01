package com.example.demosample.presentation.ui.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.presentation.component.common.ErrorView
import com.example.demosample.presentation.component.common.LoadingView
import com.example.demosample.presentation.component.common.NoMoreData
import com.example.demosample.presentation.component.common.PaginationLoader
import com.example.demosample.presentation.component.news_feed.NewsCard
import com.example.demosample.presentation.component.news_feed.SearchNewsCard
import com.example.demosample.presentation.navigation.NavGraphScreens
import com.example.demosample.presentation.theme.AppTypography
import com.example.demosample.presentation.theme.AppTypography.labelMedium12
import com.example.demosample.presentation.theme.AppTypography.labelMedium14
import com.example.demosample.presentation.theme.AppTypography.labelRegular10
import com.example.demosample.presentation.theme.AppTypography.labelRegular12
import com.example.demosample.presentation.theme.LocalAppColors
import com.example.demosample.presentation.theme.MyApplicationDemoTheme
import com.example.demosample.presentation.viewModel.SearchViewModel
import com.example.demosample.utils.constant.AppConstant

@Preview
@Composable
fun PreviewSearchRoutes() {
    MyApplicationDemoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NewsSearchBar(
                "", {

                }
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            CategoryChips {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRoutes(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        val pagingItems = viewModel.searchResult.collectAsLazyPagingItems()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background)
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = LocalAppColors.current.textPrimary,
                    )

                }
                NewsSearchBar(
                    query = viewModel.query,
                    onQueryChange = viewModel::onQueryChange,
                )
            }

            CategoryChips(
                onCategoryClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("category", it.value)

                    navController.popBackStack() // 🔥 MAIN LINE
                }
            )

            SearchResultNewsList(pagingItems)
        }
    }

}

@Composable
fun CategoryChips(onCategoryClick: (NewsCategory) -> Unit) {
    val categories = AppConstant.newsCategories
    LazyRow(
        modifier = Modifier.padding(start = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { pos ->
            val cat = categories[pos]
            AssistChip(
                modifier = Modifier,
                onClick = {
                    onCategoryClick(cat)
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = LocalAppColors.current.container,
                ),
                label = {
                    Text(
                        cat.value.uppercase(),
                        style = AppTypography.Typography.labelMedium12,
                        color = LocalAppColors.current.textPrimary
                    )
                }
            )
        }
    }
}

@Composable
fun SearchResultNewsList(
    pagingItems: LazyPagingItems<NewsModel>
) {
    LazyColumn(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(
            pagingItems.itemCount,
            key = pagingItems.itemKey { it.url }) { pos ->
            val news = pagingItems[pos]!!
            SearchNewsCard(news) {
                //onNewsClick
            }
        }
        pagingItems.apply {
            // --- CASE 1: Full Screen Error (No Internet + No Cache) ---
            if (loadState.refresh is LoadState.Error && itemCount == 0) {
                val error =
                    (loadState.refresh as LoadState.Error).error
                item {
                    ErrorView(error.message ?: "")
                }
            }
            // --- Loading View ---
            if (loadState.refresh is LoadState.Loading && itemCount > 0) {
                item { PaginationLoader() }
            }
            // --- CASE 4: End of Data ---
            if (loadState.append.endOfPaginationReached && itemCount > 0) {
                // Optional: Small "No more news" tag at bottom
                item { NoMoreData("Fetch all news!!") }
            }
            if (loadState.append is LoadState.Error && itemCount > 0) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LocalAppColors.current.background)
                            .padding(16.dp)
                    ) {
                        ErrorView(
                            "Error while loading more news!!"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 12.dp, vertical = 0.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        color = LocalAppColors.current.container
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = AppTypography.Typography.labelMedium14.copy(
                    color = LocalAppColors.current.textPrimary
                ),
                placeholder = {
                    Text(
                        "Search for news, topics",
                        style = AppTypography.Typography.labelRegular12,
                        color = LocalAppColors.current.textSecondary
                    )
                },
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
