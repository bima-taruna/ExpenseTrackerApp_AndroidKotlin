package com.bima.expensetrackerapp.presentation.component.settings.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.bima.expensetrackerapp.presentation.component.tabs.CategoriesTabs
import com.bima.expensetrackerapp.viewmodel.TabIndexViewModel
import com.bima.expensetrackerapp.viewmodel.category.AddExpenseCategoryViewModel
import com.bima.expensetrackerapp.viewmodel.category.AddIncomeCategoryViewModel
import com.bima.expensetrackerapp.viewmodel.category.CategoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySettings(
    modifier: Modifier = Modifier,
    navController: NavController,
    tabIndexViewModel: TabIndexViewModel = hiltViewModel(),
    addExpenseCategoryViewModel: AddExpenseCategoryViewModel = hiltViewModel(),
    addIncomeCategoryViewModel: AddIncomeCategoryViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val tabIndex by tabIndexViewModel.tabIndex.collectAsState()
    val context = LocalContext.current
    val composableScope = rememberCoroutineScope()

    //AddExpenseCategory
    val addExpenseCategoryState by addExpenseCategoryViewModel.addExpenseCategoryState.collectAsStateWithLifecycle()
    val addExpenseCategoryFormState by addExpenseCategoryViewModel.expenseCategoryFormState.collectAsStateWithLifecycle()
    val addExpenseCategoryValidation = addExpenseCategoryViewModel.validationEvents
    //AddIncomeCategory
    val addIncomeCategoryState by addIncomeCategoryViewModel.addIncomeCategoryState.collectAsStateWithLifecycle()
    val addIncomeCategoryFormState by addIncomeCategoryViewModel.incomeCategoryFormState.collectAsStateWithLifecycle()
    val addIncomeCategoryValidation = addIncomeCategoryViewModel.validationEvents

    var dialog by rememberSaveable {
        mutableStateOf(false)
    }

    var categoryName by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(addExpenseCategoryState) {
        addExpenseCategoryValidation.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    addExpenseCategoryViewModel.addExpenseCategory(categoryName)
                }
            }
        }
    }

    LaunchedEffect(addIncomeCategoryState) {
        addIncomeCategoryValidation.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    addIncomeCategoryViewModel.addIncomeCategory(categoryName)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        dialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "add new category"
                        )
                    }
                },
                title = {
                    Text(text = "Category")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                CategoriesTabs(navController = navController)
            }
        }
    )
    if (dialog) {
        Modal(onDismissRequest = { dialog = false }) {
            TextField(
                label = "Category Name",
                onValueChange = {
                    if (tabIndex == 0) {
                        addExpenseCategoryViewModel.onEvent(
                            CategoryFormEvent.NameChanged(it)
                        )
                    } else {
                        addIncomeCategoryViewModel.onEvent(
                            CategoryFormEvent.NameChanged(it)
                        )
                    }
                    categoryName = it
                },
                isError = if (tabIndex == 0) addExpenseCategoryFormState.nameError != null else addIncomeCategoryFormState.nameError != null,
                value = categoryName
            )
            if (addExpenseCategoryFormState.nameError != null || addIncomeCategoryFormState.nameError != null) {
                Text(
                    text = (if (tabIndex == 0) addExpenseCategoryFormState.nameError?.asString(context) else addIncomeCategoryFormState.nameError?.asString(context))!!,
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
                        if (tabIndex == 0) {
                            addExpenseCategoryViewModel.onEvent(CategoryFormEvent.Submit)
                        } else {
                            addIncomeCategoryViewModel.onEvent(CategoryFormEvent.Submit)
                        }
                        delay(500)
                        if (addExpenseCategoryState.category || addIncomeCategoryState.category) {
                            categoryViewModel.getExpenseCategory()
                            categoryViewModel.getIncomeCategory()
                            dialog = false
                        }
                    }
                }) {
                    Text(text = "Add")
                }
            }
        }
    }
}

