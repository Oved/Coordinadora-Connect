package com.ovedev.coordinadoraconnect.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.dateToString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(this)
}