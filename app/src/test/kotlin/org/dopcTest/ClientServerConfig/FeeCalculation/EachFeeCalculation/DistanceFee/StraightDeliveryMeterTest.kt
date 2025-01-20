package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.math.abs

class calcStraightDeliveryDistanceTest {

    // Test when both coordinates are the same (zero distance)
    @Test
    fun `should return 0 for identical coordinates`() {
        val venueCoordinates = listOf(0.0, 0.0)
        val userCoordinates = listOf(0.0, 0.0)
        
        val result = calcStraightDeliveryDistance(venueCoordinates, userCoordinates)
        
        assertEquals(0.0, result, "Distance between identical coordinates should be 0")
    }

    // Test with very far distance (across continents), include negative in venueCoordinates
    @Test
    fun `should calculate large distance correctly`() {
        val venueCoordinates = listOf(-1.602051, 47.155047)  // Nantes Airport coordinates 
        val userCoordinates = listOf(139.7428526, 35.6585848)   // Tokyo Tower coordinates 
        
        val result = calcStraightDeliveryDistance(venueCoordinates, userCoordinates)
        
        // The distance between Nantes and Tokyo is approximately  10033.43km
        assertTrue(abs(result - 10033000.0) < 1000.0, "Calculated distance should be approximately 10033 km")
    }


    // Test edge case with negative value in usercoordinates
    @Test
    fun `should calculate distance correctly with polar coordinates`() {
        val venueCoordinates = listOf(0.0, 90.0)  // North Pole 
        val userCoordinates = listOf(0.0, -90.0) // South Pole 
        
        val result = calcStraightDeliveryDistance(venueCoordinates, userCoordinates)
        
        // Distance between the poles is roughly 20,000 km
        assertTrue(abs(result - 20000000.0) < 100000.0, "Distance between poles should be approximately 20,000 km")
    }

}