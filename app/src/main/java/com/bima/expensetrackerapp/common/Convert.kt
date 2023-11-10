package com.bima.expensetrackerapp.common

import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

fun Double.convert(): String {

    val format = DecimalFormat("#,###")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}

fun getSymbol(): String? {
    val currency = Currency.getInstance("IDR")
    if (currency.symbol == "IDR") return "Rp"
    return currency.symbol
}