package com.example.pointtap

import com.example.pointtap.data.GeoPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class GeoPointTest {
    @Test
    fun testSerializationWithLabel() {
        val points = listOf(
            GeoPoint(10.0, 20.0, 123456789L, "Calle A"),
            GeoPoint(11.0, 21.0, 123456790L, "Calle A")
        )
        val json = Json.encodeToString(points)
        assertTrue(json.contains("\"latitude\":10.0"))
        assertTrue(json.contains("\"longitude\":20.0"))
        assertTrue(json.contains("\"timestamp\":123456789"))
        assertTrue(json.contains("\"label\":\"Calle A\""))
    }
}
