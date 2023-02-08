package ru.chiya.clounada

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.chiya.clounada.ui.theme.ClounadaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window: Window = this.window
            window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
            ClounadaTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = false
                val surface = MaterialTheme.colorScheme.surface
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = surface
                    )
                    systemUiController.setNavigationBarColor(
                        color = surface,
                        darkIcons = useDarkIcons,
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val tabRowItems = listOf(
                        TabRowItem(title = "Цирк", screen = { TheatreTab().CircusCards() }),
                        TabRowItem(title = "Премьер", screen = { TheatreTab().CircusCards() }),
                        TabRowItem(title = "Театр юного зрителя", screen = { TheatreTab().CircusCards() }),
                        TabRowItem(title = "Знаки сезонников", screen = { TheatreTab().CircusCards() }),
                        TabRowItem(title = "Театр драммы", screen = { TheatreTab().CircusCards() }))
                    ShowTabs(tabRowItems)
                }
            }
        }
    }
}

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

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowTabs(tabRowItems: List<TabRowItem>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // TODO: mb move to bottom?
    Column {
        ScrollableTabRow(contentColor = MaterialTheme.colorScheme.secondary,
            edgePadding = 0.dp,
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