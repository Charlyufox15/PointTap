package com.example.pointtap.data

import kotlinx.serialization.Serializable

@Serializable
data class GeoJsonFeatureCollection(
    val type: String = "FeatureCollection",
    val features: List<GeoJsonFeature>
)

@Serializable
data class GeoJsonFeature(
    val type: String = "Feature",
    val properties: Map<String, String>,
    val geometry: GeoJsonGeometry
)

@Serializable
data class GeoJsonGeometry(
    val type: String = "LineString",
    val coordinates: List<List<Double>> // [[lon, lat], [lon, lat], ...]
)
