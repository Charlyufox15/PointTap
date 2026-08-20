package com.example.pointtap

import com.example.pointtap.data.GeoPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class GeoPointTest {
    @Test
    fun testSerializationAsSimpleCoordinates() {
        val points = listOf(
            GeoPoint(10.0, 20.0),
            GeoPoint(11.0, 21.0)
        )
        val simplePoints = points.map { listOf(it.latitude, it.longitude) }
        val json = Json.encodeToString(simplePoints)
        
        // Should look like [[10.0,20.0],[11.0,21.0]]
        assertTrue(json.startsWith("[["))
        assertTrue(json.contains("10.0,20.0"))
        assertTrue(!json.contains("\"type\""))
    }
}
