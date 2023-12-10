package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextArea(
    modifier: Modifier = Modifier,
    description: MutableState<String>,
) {
    val maxLength = 100
    OutlinedTextField(
        label = {
            Text(
                text = "Description",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },
        value = description.value,
        onValueChange = {
            if (it.length <= maxLength) {
                description.value = it
            }
        },
        singleLine = false,
        maxLines = 5,
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
    )
}