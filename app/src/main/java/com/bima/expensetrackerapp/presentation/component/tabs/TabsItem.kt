package com.bima.expensetrackerapp.presentation.component.tabs

import androidx.navigation.NavController
import com.bima.expensetrackerapp.domain.model.TabItem

fun tabsItem(
    navController: NavController
): List<TabItem> {
    return listOf(
        TabItem(
            title = "Expense",
            screen = { ExpenseTab(navController = navController)}
        ),
        TabItem(
            title = "Income",
            screen = { IncomeTab(navController = navController) }
        ),
    )
}