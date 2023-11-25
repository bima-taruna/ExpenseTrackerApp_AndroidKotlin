package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import com.bima.expensetrackerapp.common.CurrencyVisualTransformation

@Composable
fun CurrencyTextField(
    text: MutableState<String>,
    amount:MutableState<Double>
) {
    TextField(
        value = text.value,
        onValueChange = {
            if (it.startsWith("0")) {
                text.value = ""
                amount.value = 0.0
            } else {
                text.value = it
                amount.value = it.toDouble()
            }
        },
        visualTransformation = CurrencyVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )


}