package com.bima.expensetrackerapp.presentation.component.tabs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.CategoryFormEvent
import com.bima.expensetrackerapp.presentation.component.Modal
import com.bima.expensetrackerapp.presentation.component.form.TextField
import com.bima.expensetrackerapp.presentation.component.shapes_container.SmallCard
import com.bima.expensetrackerapp.viewmodel.category.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.category.UpdateCategoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExpenseTabCategory(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    updateCategoryViewModel: UpdateCategoryViewModel = hiltViewModel()
) {
    var dialog by rememberSaveable {
        mutableStateOf(false)
    }
    var categoryId by rememberSaveable {
        mutableStateOf("")
    }
    var categoryName by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyListState()
    val composableScope = rememberCoroutineScope()
    val expenseCategoryState by categoryViewModel.categoriesExpenseState.collectAsStateWithLifecycle()
    val expenseCategoryDetailState by categoryViewModel.categoryState.collectAsStateWithLifecycle()
    //UpdateCategory
    val updateCategoryFormState by updateCategoryViewModel.updateCategoryFormState.collectAsStateWithLifecycle()
    val updateCategoryState by updateCategoryViewModel.updateCategoryState.collectAsStateWithLifecycle()
    val updateCategoryValidation = updateCategoryViewModel.validationEvents

    LaunchedEffect(context, expenseCategoryState.categories) {
        categoryViewModel.getExpenseCategory()
    }

//    LaunchedEffect(dialog) {
//
//        Log.d("categoryId", categoryId)
//        Log.d("categoryName", categoryName)
//    }

    LaunchedEffect(updateCategoryState) {
        updateCategoryValidation.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    updateCategoryViewModel.updateCategory(categoryId, categoryName)
                }
            }
        }
    }
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = lazyColumnListState
        ) {
            expenseCategoryState.categories?.let { category ->
                items(items = category, key = { id ->
                    id.id ?: ""
                }) {
                    SmallCard(onClick = {
                        composableScope.launch {
                            categoryId = it.id ?: ""
                            delay(1000)
                            categoryViewModel.getCategoryById(categoryId)
                            Log.d("categoryName", expenseCategoryDetailState.toString())
                            dialog = true
                        }
                    }) {
                        Row(
                            modifier = modifier.padding(16.dp)
                        ) {
                            Text(text = it.name ?: "")
                        }
                    }
                }
            }
        }
        if(dialog) {
            Modal(onDismissRequest = { dialog = false }) {
                TextField(
                    label = "Category Name",
                    onValueChange = {
                        updateCategoryViewModel.onEvent(
                            CategoryFormEvent.NameChanged(it)
                        )
                        categoryName = it
                    },
                    isError = updateCategoryFormState.nameError != null,
                    value = expenseCategoryDetailState.category?.name ?: categoryName
                )
                if (updateCategoryFormState.nameError != null) {
                    Text(
                        text =  updateCategoryFormState.nameError?.asString(context) ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        composableScope.launch {
                            updateCategoryViewModel.onEvent(CategoryFormEvent.Submit)
                            delay(500)
                            if (updateCategoryState.category) {
                                categoryViewModel.getExpenseCategory()
                                dialog = false
                            }
                        }
                    }) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}