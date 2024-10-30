package com.ovedev.coordinadoraconnect.utils

import android.util.Base64

fun String.decodeBase64ToBytes(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}