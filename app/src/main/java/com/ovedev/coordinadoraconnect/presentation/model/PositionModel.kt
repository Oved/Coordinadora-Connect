package com.ovedev.coordinadoraconnect.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val latitude: Double,
    val longitude: Double
): Parcelable
