package com.bima.expensetrackerapp.common

import org.junit.Assert.*

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class DateFormatterKtTest {

    @Test
    fun toDate() {
        val dateString = "2023-12-01"
        val expectedDate = "01/12/2023"

        val testDate = dateString.toDate()

        assertThat(testDate).isEqualTo(expectedDate)
    }
}