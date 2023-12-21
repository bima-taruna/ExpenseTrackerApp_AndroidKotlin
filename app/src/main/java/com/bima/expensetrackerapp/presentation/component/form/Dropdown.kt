package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bima.expensetrackerapp.viewmodel.state.CategoryState
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    selectedCategory:MutableState<String>,
    category:MutableState<String>,
    categoryState:CategoryState,
    changeValue:() -> Unit,
    isError:Boolean
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }) {
        OutlinedTextField(
            label = {
                Text(
                    text = "Category",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            value = selectedCategory.value,
            onValueChange = {},
            readOnly = true,
            isError = isError,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier.menuAnchor().fillMaxWidth(0.80f)
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            if (categoryState.isLoading) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                categoryState.category?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name.toString()) },
                        onClick = {
                            selectedCategory.value = item.name.toString()
                            category.value = item.id.toString()
                            expanded.value = false
                            changeValue()
                        }
                    )
                }
            }
        }
    }
}