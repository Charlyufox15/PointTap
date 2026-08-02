package com.example.pointtap

import com.example.pointtap.data.GeoPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class GeoPointTest {
    @Test
    fun testSerialization() {
        val points = listOf(
            GeoPoint(10.0, 20.0, 123456789L),
            GeoPoint(11.0, 21.0, 123456790L)
        )
        val json = Json.encodeToString(points)
        assertTrue(json.contains("\"latitude\":10.0"))
        assertTrue(json.contains("\"longitude\":20.0"))
        assertTrue(json.contains("\"timestamp\":123456789"))
    }
}
