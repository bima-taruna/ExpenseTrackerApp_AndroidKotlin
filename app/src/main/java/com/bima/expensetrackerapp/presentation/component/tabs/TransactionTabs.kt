@file:OptIn(ExperimentalFoundationApi::class)

package com.bima.expensetrackerapp.presentation.component.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun TransactionTabs(
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabsItem().forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = {
                        Text(
                            text = tabItem.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }
        HorizontalPager(
            pageCount = tabsItem().size,
            state = pagerState
        ) {
            tabsItem()[pagerState.currentPage].screen()
        }
    }
}