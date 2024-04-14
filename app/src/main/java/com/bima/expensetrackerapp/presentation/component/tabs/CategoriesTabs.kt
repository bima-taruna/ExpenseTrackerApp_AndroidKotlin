@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.bima.expensetrackerapp.presentation.component.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.domain.model.TabItem
import com.bima.expensetrackerapp.viewmodel.TabIndexViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoriesTabs(
    modifier: Modifier = Modifier,
    tabIndexViewModel: TabIndexViewModel = hiltViewModel(),
    navController: NavController,
) {
    val pagerState = rememberPagerState {tabsItem(navController = navController).size}
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage, modifier.padding(bottom = 8.dp)) {
            categoriesItem(navController = navController).forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = {
                        Text(
                            text = tabItem.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    onClick = {
                        tabIndexViewModel.saveTabIndex(index)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
//            pageCount = categoriesItem(navController = navController).size,
            state = pagerState,
            userScrollEnabled = false
        ) {
            categoriesItem(navController = navController)[pagerState.currentPage].screen()
        }
    }
}


fun categoriesItem(
    navController: NavController,
): List<TabItem> {
    return listOf(
        TabItem(
            title = "Expense",
            screen = { ExpenseTabCategory(navController = navController) }
        ),
        TabItem(
            title = "Income",
            screen = { IncomeTabCategory(navController = navController)}
        )
    )
}