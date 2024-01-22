package com.bima.expensetrackerapp.common

import org.junit.Assert.*

import org.junit.Test
import com.google.common.truth.Truth.assertThat


class StringFormatterKtTest {

    @Test
    fun stringFormatter() {
        val string = "expense"
        val formattedString = mutableListOf<Char>()

        for (i in string.indices) {
            formattedString.add(string[i])
        }
        formattedString[0] = formattedString[0].uppercaseChar()

        assertThat(formattedString.joinToString("")).isEqualTo("Expense")
    }
}