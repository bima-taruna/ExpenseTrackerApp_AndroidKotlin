package com.bima.expensetrackerapp.common

import java.util.Currency

fun getSymbol(): String? {
    val currency = Currency.getInstance("IDR")
    if (currency.symbol == "IDR") return "Rp"
    return currency.symbol
}