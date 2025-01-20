
package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DistanceRangeTest {

    @Test
    fun `should return null for an empty list`() {
        val distanceRanges = emptyList<DistanceRange>()
        val result = findDistanceRange(distanceRanges, 100.0)
        assertNull(result) 
    }

    @Test // General case
    fun `should return the correct range for a distance within bounds`() {
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0, 0.0, null),
            DistanceRange(500, 1000, 100, 1.0, null),
            DistanceRange(1000, 0, 0, 0.0, null)
        )
        val result = findDistanceRange(distanceRanges, 750.0)
        assertEquals(result, distanceRanges[1]) 
    }

    @Test
    fun `should return null for distances above min of above closed ranges`() {
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0, 0.0, null),
            DistanceRange(500, 1000, 100, 1.0, null),
            DistanceRange(1000, 0, 0, 0.0, null) 
        )
        val result = findDistanceRange(distanceRanges, 154209.2412)
        assertEquals(result, null) 
    }


    @Test
    fun `should return the correct range for a distance exactly at the lower boundary`() {
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0, 0.0, null),
            DistanceRange(500, 1000, 100, 1.0, null),
            DistanceRange(1000, 0, 0, 0.0, null)
        )
        val result = findDistanceRange(distanceRanges, 500.0)
        assertEquals(result, distanceRanges[1]) 
    }

    @Test
    fun `should return null for a distance exactly at the upper boundary`() {
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0, 0.0, null),
            DistanceRange(500, 1000, 100, 1.0, null),
            DistanceRange(1000, 0, 0, 0.0, null)
        )
        val result = findDistanceRange(distanceRanges, 500.0)
        assertEquals(result, distanceRanges[1]) 
    }


    @Test
    fun `should handle a single open-ended range`() {
        val distanceRanges = listOf(
            DistanceRange(0, 0, 0, 0.0, null) 
        )
        val result = findDistanceRange(distanceRanges, 10000.0)
        assertNull(result)
    }
}
