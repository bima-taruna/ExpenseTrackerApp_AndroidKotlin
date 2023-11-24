package com.bima.expensetrackerapp.presentation.component

import androidx.compose.runtime.Composable
import java.text.DecimalFormat

@Composable
fun CurrencyTextField() {
    val symbols = DecimalFormat().decimalFormatSymbols
    val thousandSeparator = symbols.groupingSeparator
    val decimalSeparator = symbols.decimalSeparator


}