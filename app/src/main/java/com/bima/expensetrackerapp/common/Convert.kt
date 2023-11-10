package com.bima.expensetrackerapp.common

import java.text.DecimalFormat

fun Double.convert(): String {
    val format = DecimalFormat("#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}