package com.bima.expensetrackerapp.presentation.component.tabs

import com.bima.expensetrackerapp.domain.model.TabItem

fun tabsItem(): List<TabItem> {
    return listOf(
        TabItem(
            title = "Expense",
            screen = { ExpenseTabs()}
        ),
        TabItem(
            title = "Income",
            screen = { IncomeTabs() }
        ),
    )
}