package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.bima.expensetrackerapp.common.CurrencyVisualTransformation

@Composable
fun CurrencyTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label:String,
    amount: MutableState<Double>,
    onValueChange: () -> Unit,
    isError: Boolean,
) {
    OutlinedTextField(
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },
        isError = isError,
        prefix = {
            Text(
                text = "Rp",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        },
        value = text.value,
        onValueChange = {
            if (it == "") {
                text.value = ""
                amount.value = 0.0
            } else {
                text.value = it
                println(it)
                amount.value = it.toDouble()
            }
            onValueChange()
        },
        visualTransformation = CurrencyVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        modifier = modifier
    )
}