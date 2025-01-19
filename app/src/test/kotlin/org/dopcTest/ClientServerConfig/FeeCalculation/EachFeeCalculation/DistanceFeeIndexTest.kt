// baseDeliveryFee negative
// all 0
// 400 error?, when delivery distance is above than the max distance

// 0.5 round up, 0.4 round down(distanceRangePricingConfig.b * meterStraightDeliveryDistance / 10).roundToInt() //　Rounded for the nearest integer

package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import io.ktor.server.plugins.BadRequestException
import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange
import kotlin.math.roundToInt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DistanceFeeTest {

    // Example from assignment(Helsinki)
    @Test
    fun `should calculate distance fee correctly within bounds`() {
        val baseDeliveryFee = 190
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143)  
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0,  0.0, null),
            DistanceRange(500, 1000, 100, 0.0, null),
            DistanceRange(1000, 1500, 200, 0.0, null),
            DistanceRange(1500, 2000, 200,  1.0, null),
            DistanceRange(2000, 0, 0, 0.0, null)
        )

        val distanceFee = DistanceFee()
        val result = distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)


        assertEquals(190, result.distanceFeeTotal, "The calculated fee is incorrect.")
        assertEquals(177, result.deliveryDistanceInMeter, "The delivery distance is incorrect.")
    }


    // Test when the user is outside of the delivery area (distance range is invalid)
    @Test
    fun `should throw BadRequestException for invalid delivery area`() {
        val baseDeliveryFee = 190
        val userCoordinates = listOf(10.0, 10.0)
        val venueCoordinates = listOf(10.1, 10.1)
        val distanceRanges = listOf(
            DistanceRange(0, 500, 0,  0.0, null),
            DistanceRange(500, 1000, 100, 0.0, null),
            DistanceRange(1000, 1500, 200, 0.0, null),
            DistanceRange(1500, 2000, 200,  1.0, null),
            DistanceRange(2000, 0, 0, 0.0, null)
        )

        val distanceFee = DistanceFee()

        assertFailsWith<BadRequestException> {
            distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)
        }
    }


    // Test invalid coordinates (NaN or out of bounds)
    @Test
    fun `should throw exception for invalid coordinates (NaN)`() {
        val baseDeliveryFee = 1000
        val userCoordinates = listOf(Double.NaN, 10.0) // Invalid latitude
        val venueCoordinates = listOf(10.0, 10.0)
        val distanceRanges = listOf(
            DistanceRange(0, 500, 100, 0.5, null),
            DistanceRange(500, 1000, 200, 0.6, null)
        )

        val distanceFee = DistanceFee()

        assertFailsWith<BadRequestException> {
            distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)
        }
    }

    // Test empty distanceRanges (edge case)
    @Test
    fun `should throw exception for empty distanceRanges`() {
        val baseDeliveryFee = 1000
        val userCoordinates = listOf(10.0, 10.0)
        val venueCoordinates = listOf(10.1, 10.1)
        val distanceRanges: List<DistanceRange> = emptyList()  // Empty list of ranges

        val distanceFee = DistanceFee()

        assertFailsWith<BadRequestException> {
            distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)
        }
    }

    // Test 0
    @Test
    fun `result shoult be 0, when all prices were 0 `() {
        val baseDeliveryFee = 0
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 
        val distanceRanges = listOf(
            DistanceRange(0, 1000, 0, 0.0, null),
            DistanceRange(1000, 0, 0, 0.0, null)
        )

        val distanceFee = DistanceFee()
        val result = distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)


        assertEquals(0, result.distanceFeeTotal, "The calculated fee is incorrect.")
    }
   
    
    // Test ngative
    @Test
    fun `it should throw exeption, when negative value calculated `() {
        val baseDeliveryFee = -10
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 
        val distanceRanges = listOf(
            DistanceRange(0, 1000, -1, 0.0, null),
            DistanceRange(1000, 0, 0, 0.0, null)
        )

       val distanceFee = DistanceFee()
        assertFailsWith<Exception> {
            distanceFee.distanceFee(baseDeliveryFee, userCoordinates, venueCoordinates, distanceRanges)
        }
        
    }

}
