package ru.chiya.clounada.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@Composable
fun TabScreen(function: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        function()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowTabs(tabRowItems: List<TabRowItem>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // why it lags :sadcat:
    Column {
        ScrollableTabRow(contentColor = MaterialTheme.colorScheme.secondary,
            edgePadding = 0.dp,
            backgroundColor = Color.Transparent,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colorScheme.secondary
                )
            }) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = item.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
        }
        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}