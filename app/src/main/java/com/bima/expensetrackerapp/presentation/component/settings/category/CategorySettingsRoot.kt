package com.bima.expensetrackerapp.presentation.component.settings.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel

@Composable
fun CategorySettingsRoot(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val categoryExpenseState by categoryViewModel.categoryExpenseState.collectAsStateWithLifecycle()
    val categoryIncomeState by categoryViewModel.categoryIncomeState.collectAsStateWithLifecycle()
    CategorySettings()
}