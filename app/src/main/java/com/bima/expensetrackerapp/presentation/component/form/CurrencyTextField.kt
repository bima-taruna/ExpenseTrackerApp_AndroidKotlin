package com.bima.expensetrackerapp.presentation.component.form

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import com.bima.expensetrackerapp.common.CurrencyVisualTransformation

@Composable
fun CurrencyTextField(
    text: MutableState<String>,
    amount:MutableState<Double>
) {
    OutlinedTextField(
        label = {
            Text(
                text = "Amount",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },
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
        },
        visualTransformation = CurrencyVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )


}