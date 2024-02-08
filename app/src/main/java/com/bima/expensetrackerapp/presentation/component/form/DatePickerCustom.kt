@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent
import java.util.Date

@Composable
fun DatePickerCustom(
    confirmDate: () -> Unit,
    dismissRequest:()->Unit,
    dismiss: () -> Unit,
    state: DatePickerState,
) {
    DatePickerDialog(
        onDismissRequest = {
            dismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
               confirmDate()
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = state,
        )
    }
}