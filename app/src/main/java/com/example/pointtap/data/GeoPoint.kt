package com.example.pointtap.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    @Transient
    val timestamp: Long = System.currentTimeMillis(),
    @Transient
    val label: String? = null
)
