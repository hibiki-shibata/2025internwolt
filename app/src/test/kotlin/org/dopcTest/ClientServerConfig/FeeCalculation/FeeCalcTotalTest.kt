package org.dopc.clientserverconfig.feecalculation.feecalctotal

import org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi.RequestRestaurantData
import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange

import org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge.MinSurchargeFee
import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFee
import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFeeInfo

import io.ktor.server.plugins.BadRequestException

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DeliveryFeeTotalTest {

    // Example DistanceRange for testing
    private val distanceRanges = listOf(
            DistanceRange(0, 500, 0,  0.0, null),
            DistanceRange(500, 1000, 100, 0.0, null),
            DistanceRange(1000, 1500, 200, 0.0, null),
            DistanceRange(1500, 2000, 200,  1.0, null),
            DistanceRange(2000, 0, 0, 0.0, null)
    )

    // Test when cart value is above the minimum order surcharge threshold
    @Test
    fun `should calculate total price correctly when cart value is above minimum surcharge`() {
        val cartValue = 1100
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143)          

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

        // Ensure no surcharge is applied and correct calculation of fees
        assertEquals(1290, result.totalPurchasePrice)
        assertEquals(0, result.order_minimum_surcharge)
        assertEquals(cartValue, result.cart_value)
        assertEquals(190, result.delivery_fee) 
        assertEquals(177, result.delivery_distance)
    }


    // Test when cart value is equal to minimum surcharge threshold
    @Test
    fun `should calculate total price correctly when cart value equals minimum surcharge`() {
        val cartValue = 1000
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

        // No surcharge should be applied
        assertEquals(1190, result.totalPurchasePrice)
        assertEquals(0, result.order_minimum_surcharge)
        assertEquals(cartValue, result.cart_value)
        assertEquals(190, result.delivery_fee) 
        assertEquals(177, result.delivery_distance)
    }


    // Test when cart value is below the minimum surcharge threshold
    @Test
    fun `should apply surcharge when cart value is below minimum surcharge threshold`() {
        val cartValue = 900
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

        // No surcharge should be applied
        assertEquals(1190, result.totalPurchasePrice)
        assertEquals(100, result.order_minimum_surcharge)
        assertEquals(cartValue, result.cart_value)
        assertEquals(190, result.delivery_fee) 
        assertEquals(177, result.delivery_distance)
    }

    // Test with negative cart value to check if exception is thrown
    @Test
    fun `should throw exception for negative total price calculation`() {
        val cartValue = -900
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        assertFailsWith<Exception> {
            deliveryFeeTotal.deliveryFeeTotalCalculation()
        }
    }

    // Test with a large cart value to check for correct calculation
    @Test
    fun `should handle large cart value correctly`() {
        val cartValue = 10000000
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143) 

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

        assertEquals(10000190, result.totalPurchasePrice)
        assertEquals(0, result.order_minimum_surcharge)
        assertEquals(cartValue, result.cart_value)
        assertEquals(190, result.delivery_fee) 
        assertEquals(177, result.delivery_distance)
    }

    // Test when there is no distance range match (invalid distance)
    @Test
    fun `should throw exception for invalid delivery distance`() {
        val cartValue = 1100
        val baseDeliveryFee = 190
        val orderMinimumNoSurcharge = 1000
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(240.92813512, 600.17012143) 

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)
        
        assertFailsWith<BadRequestException> {
            deliveryFeeTotal.deliveryFeeTotalCalculation()
        }
    }
    
    // Zero
    @Test
    fun `should return only delivery based fee, when other values are 0`() {
        val cartValue = 0
        val baseDeliveryFee = 0
        val orderMinimumNoSurcharge = 0
        val userCoordinates = listOf(24.93087, 60.17094)
        val venueCoordinates = listOf(24.92813512, 60.17012143)          

        val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinates, venueCoordinates, distanceRanges)

        val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

        // Ensure no surcharge is applied and correct calculation of fees
        assertEquals(0, result.totalPurchasePrice)
        assertEquals(0, result.order_minimum_surcharge)
        assertEquals(cartValue, result.cart_value)
        assertEquals(0, result.delivery_fee) 
        assertEquals(177, result.delivery_distance) 
    }
}
