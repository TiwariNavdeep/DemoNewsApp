package com.example.demosample.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.presentation.component.common.ErrorView
import com.example.demosample.presentation.component.common.LoadingView
import com.example.demosample.presentation.component.common.MyTabRowCategory
import com.example.demosample.presentation.component.home.HomeTopBar
import com.example.demosample.presentation.component.news_feed.NewsList
import com.example.demosample.presentation.navigation.NavGraphScreens
import com.example.demosample.presentation.theme.AppTypography.labelMedium12
import com.example.demosample.presentation.theme.AppTypography.labelRegular12
import com.example.demosample.presentation.theme.LocalAppColors
import com.example.demosample.presentation.viewModel.HomeViewModel
import com.example.demosample.utils.constant.AppConstant
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoutes(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isNetWorkAvailable by viewModel.isOnline.collectAsState()

    Scaffold { innerPadding ->
        val context = LocalContext.current

        val savedStateHandle =
            navController.currentBackStackEntry?.savedStateHandle

        val categorySelected by savedStateHandle
            ?.getStateFlow("category", "general")
            ?.collectAsState()
            ?: remember { mutableStateOf("general") }


        val pagerState = rememberPagerState(initialPage = 0) {
            AppConstant.newsCategories.size
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LocalAppColors.current.background)
        ) {
            HomeTopBar(
                isNetWorkAvailable,
                onSearchClick = {
                    val category = AppConstant.newsCategories[pagerState.currentPage]
                    navController.currentBackStackEntry?.savedStateHandle
                        ?.set("category", category.value)
                    navController.navigate(
                        NavGraphScreens.Search.route
                    )
                })

            NewsCategoryHome(pagerState)

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background),
                state = pagerState
            ) { position ->
                if (position == pagerState.currentPage) {
                    val category = AppConstant.newsCategories[position]
                    //show category News
                    NewsListForCategoryHome(
                        category,
                        viewModel,
                        onNewsClick = {
                            if(isNetWorkAvailable){
                                navController.navigate(
                                    NavGraphScreens.WebView.createRoute(it.url)
                                )
                            }else{
                                Toast.makeText(context,"Network not available!!", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }

        LaunchedEffect(
            categorySelected
        ) {
            val catIndex = AppConstant.newsCategories.indexOfLast {
                it.value.equals(categorySelected, true)
            }.coerceAtLeast(0)
            if (catIndex != pagerState.currentPage) {
                pagerState.scrollToPage(catIndex)
            }
        }
    }
}


@Composable
fun NewsCategoryHome(
    pagerState: PagerState
) {

    MyTabRowCategory(
        selectedTabIndex = pagerState.currentPage,
        tabCount = AppConstant.newsCategories.size,
        containerColor = LocalAppColors.current.background,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val scope = rememberCoroutineScope()

        AppConstant.newsCategories.forEachIndexed { index, item ->
            val selected = pagerState.currentPage == index
            Tab(
                selected = selected,
                modifier = Modifier
                    .background(Color.Unspecified),
                unselectedContentColor = LocalAppColors.current.unSelectedText,
                selectedContentColor = LocalAppColors.current.selectedText,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index) // 🔥 key line
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = item.value.uppercase(java.util.Locale.getDefault()),
                        style = (if (selected) MaterialTheme.typography.labelMedium12 else
                            MaterialTheme.typography.labelRegular12),
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NewsListForCategoryHome(
    category: NewsCategory,
    viewModel: HomeViewModel,
    onNewsClick: (NewsModel) -> Unit
) {
    val isNetWorkAvailable by viewModel.isOnline.collectAsState()

    val newsPagingItems: LazyPagingItems<NewsModel> =
        viewModel.homeUIState.collectAsLazyPagingItems()
    LaunchedEffect(category) {
        viewModel.getNews(category)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NewsList(
            newsPagingItems = newsPagingItems,
            onNewsClick = onNewsClick
        )
        newsPagingItems.apply {
            // --- CASE 1: Full Screen Error (No Internet + No Cache) ---
            if (loadState.refresh is LoadState.Error && itemCount == 0) {
                ErrorView(
                    if(isNetWorkAvailable)
                        "No news articles were found in this category. Try searching for something else or check back later."
                    else{
                        "It looks like you're not connected to the internet. We'll show you the latest saved news, but new stories won't load until you're back online."
                    }
                )
            }
            // --- Loading View ---
            if (loadState.refresh is LoadState.Loading && itemCount == 0) {
                LoadingView()
            }
        }
    }
}
