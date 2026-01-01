package com.example.demosample.presentation.component.news_feed

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.presentation.component.common.LoadMoreErrorView
import com.example.demosample.presentation.component.common.NoMoreData
import com.example.demosample.presentation.component.common.PaginationLoader


@Composable
fun NewsFeedErrorViews(newsPagingItems: LazyPagingItems<NewsModel>) {
    /*
       * show loading when load next page data*/
    if (newsPagingItems.loadState.refresh is LoadState.Loading && newsPagingItems.itemCount > 0) {
        PaginationLoader()
    }
    /*
    * Show Error when load all news */
    if (newsPagingItems.loadState.append.endOfPaginationReached && newsPagingItems.itemCount > 0) {
        NoMoreData("No more news available!!")
    }
    /*
    * Show Error when load next page */
    if (newsPagingItems.loadState.append is LoadState.Error && newsPagingItems.itemCount > 0) {
        val error =
            (newsPagingItems.loadState.append as LoadState.Error).error
        LoadMoreErrorView(error.message ?: "Failed to load..")
    }
}