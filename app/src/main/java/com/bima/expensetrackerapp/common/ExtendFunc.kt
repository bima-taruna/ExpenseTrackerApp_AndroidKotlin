package com.bima.expensetrackerapp.common

fun String.isAlphabet(): Boolean {
    return Regex("^[A-Za-z ]+\$").matches(this)
}

fun String.isCurrency():Boolean {
    return Regex("^\\d*\\.?\\d*\$").matches(this)
}

fun String.stringFormatter():String {
    val formattedString = mutableListOf<Char>()
    for (i in this.indices) {
        formattedString.add(this[i])
    }
    formattedString[0] = formattedString[0].uppercaseChar()
    return formattedString.joinToString("")
}