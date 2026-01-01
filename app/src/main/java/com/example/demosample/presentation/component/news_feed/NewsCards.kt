package com.example.demosample.presentation.component.news_feed

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.presentation.component.common.LoadMoreErrorView
import com.example.demosample.presentation.component.common.NoMoreData
import com.example.demosample.presentation.component.common.PaginationLoader
import com.example.demosample.presentation.theme.AppTypography
import com.example.demosample.presentation.theme.AppTypography.labelMedium12
import com.example.demosample.presentation.theme.AppTypography.labelRegular10
import com.example.demosample.presentation.theme.AppTypography.labelRegular12
import com.example.demosample.presentation.theme.LocalAppColors
import com.example.demosample.presentation.theme.MyApplicationDemoTheme

@Preview
@Composable
fun PreviewNewsCard() {
    MyApplicationDemoTheme {
        NewsCard(
            NewsModel()
        ) { }
    }
}


@Composable
fun NewsList(
    newsPagingItems: LazyPagingItems<NewsModel>,
    onNewsClick: (NewsModel) -> Unit = {}
) {
    if (newsPagingItems.itemCount > 0) {
        val listState = rememberLazyListState()
        val snapBehavior = rememberSnapFlingBehavior(
            lazyListState = listState
        )
        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior
        ) {
            items(
                count = newsPagingItems.itemCount,
                key = newsPagingItems.itemKey { it.url }
            ) {
                val news = newsPagingItems[it]!!
                Box(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .fillMaxWidth()
                ) {
                    NewsCard(news, onNewsClick)
                }
            }

            item {
                NewsFeedErrorViews(newsPagingItems)
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsModel, onClick: (NewsModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 1.dp, vertical = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.background(Color.White)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.6f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .blur(30.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.Low
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = news.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f)
            ) {

                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    maxLines = 5,
                    lineHeight = 24.sp,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(
                        text = news.publishedAt + " | ",
                        style = AppTypography.Typography.labelRegular12,
                        color = LocalAppColors.current.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = news.sourceName,
                        style = AppTypography.Typography.labelMedium12,
                        color = Color.Blue.copy(.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

            }
            Column(
                modifier = Modifier
                    .clickable(
                        indication = LocalIndication.current, // <- explicitly
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClick(news)
                    }
                    .fillMaxWidth()
                    .background(
                        LocalAppColors.current.container
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Tap here to read more.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium
                    ),
                    color = LocalAppColors.current.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SearchNewsCard(news: NewsModel, onClick: (NewsModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalAppColors.current.container)
        ) {

            Box(
                modifier = Modifier
                    .width(100.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = news.title,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(100.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f)
            ) {

                Text(
                    text = news.title,
                    style = MaterialTheme.typography.labelRegular12,
                    color = LocalAppColors.current.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = news.description,
                    style = MaterialTheme.typography.labelRegular10,
                    color = LocalAppColors.current.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        text = news.publishedAt + " | ",
                        style = AppTypography.Typography.labelRegular12,
                        color = LocalAppColors.current.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = news.sourceName,
                        style = AppTypography.Typography.labelMedium12,
                        color = LocalAppColors.current.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
