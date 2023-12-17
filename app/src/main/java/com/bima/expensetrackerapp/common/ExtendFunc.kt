package com.bima.expensetrackerapp.common

fun String.isAlphabet(): Boolean {
    return Regex("^[A-Za-z]+\$").matches(this)
}