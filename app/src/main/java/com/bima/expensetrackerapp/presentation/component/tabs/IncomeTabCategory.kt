package com.bima.expensetrackerapp.presentation.component.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel

@Composable
fun IncomeTabCategory(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyListState()
    val incomeCategoryState by categoryViewModel.categoryIncomeState.collectAsStateWithLifecycle()
    LaunchedEffect(context) {
        categoryViewModel.getIncomeCategory()
    }
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = lazyColumnListState
        ) {
            incomeCategoryState.category?.let { category ->
                items(items = category, key = { id ->
                    id.id ?: ""
                }) {
                    Text(text = it.name ?: "")
                }
            }
        }
    }
}