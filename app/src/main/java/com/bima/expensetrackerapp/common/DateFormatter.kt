package com.bima.expensetrackerapp.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatIsoToString(): String? {
    val actual = OffsetDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return actual.format(formatter)
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun String.formatDateToString() :String? {
//    return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd-mm-yyyy")).toString()
//}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd-mm-yyyy"))
}