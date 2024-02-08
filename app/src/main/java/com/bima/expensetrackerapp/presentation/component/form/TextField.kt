package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent

@Composable
fun TextField(
    modifier:Modifier = Modifier,
    label:String,
    onValueChange:(it:String)->Unit,
    isError:Boolean,
    value:String
) {
    OutlinedTextField(
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },
        value = value,
        onValueChange = {
          onValueChange(it)
        },
        singleLine = true,
        isError = isError,
        modifier = modifier.fillMaxWidth()
    )
}