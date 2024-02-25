package com.bima.expensetrackerapp.common

import org.junit.Assert.*

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class ExtendFuncKtTest {

    @Test
    fun isCurrency() {
        val regex = Regex("^\\d*\\.?\\d*\$")


        assertThat("12.30").isEqualTo(regex.matches("12.30"))
    }
}