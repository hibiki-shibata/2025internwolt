// exampine if the returned value is as expected
// negative values
// 0 
package org.dopc.clientserverconfig.feecalculation.feecalctotal

import org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi.RequestRestaurantData
import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange
// import extractrequireddata.VenueStaticData
// import minfeesurcharge.minFreeSurcharge() // Function
import org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge.MinSurchargeFee

import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFee
import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFeeInfo

import io.ktor.server.plugins.BadRequestException

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// class DeliveryFeeTotalTest {

//     // Sample DistanceRange for testing purposes
//     private val distanceRanges = listOf(
//         DistanceRange(0, 500, 0,  0.0, null),
//             DistanceRange(500, 1000, 100, 0.0, null),
//             DistanceRange(1000, 1500, 200, 0.0, null),
//             DistanceRange(1500, 2000, 200,  1.0, null),
//             DistanceRange(2000, 0, 0, 0.0, null)
//     )

//     // Test when cart value is above the minimum order surcharge threshold
//     @Test
//     fun `should calculate total price correctly when cart value is above minimum surcharge`() {
//         val cartValue = 1200
//         val baseDeliveryFee = 100
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(34.0, 136.0)

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

//         // Ensure no surcharge is applied and correct calculation of fees
//         assertEquals(1300, result.totalPurchasePrice)
//         assertEquals(0, result.order_minimum_surcharge)
//         assertEquals(cartValue, result.cart_value)
//         assertEquals(100, result.delivery_fee) // Assuming the distance fee is 100
//         assertEquals(1000, result.delivery_distance) // Assuming this based on the mock distances
//     }

//     // Test when cart value is equal to minimum surcharge threshold
//     @Test
//     fun `should calculate total price correctly when cart value equals minimum surcharge`() {
//         val cartValue = 1000
//         val baseDeliveryFee = 100
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(34.0, 136.0)

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

//         // No surcharge should be applied
//         assertEquals(1100, result.totalPurchasePrice)
//         assertEquals(0, result.order_minimum_surcharge)
//         assertEquals(cartValue, result.cart_value)
//         assertEquals(100, result.delivery_fee)
//         assertEquals(1000, result.delivery_distance)
//     }

//     // Test when cart value is below the minimum surcharge threshold
//     @Test
//     fun `should apply surcharge when cart value is below minimum surcharge threshold`() {
//         val cartValue = 800
//         val baseDeliveryFee = 100
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(34.0, 136.0)

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

//         // Surcharge should be applied, since cart value is less than the minimum surcharge
//         assertEquals(1200, result.totalPurchasePrice)
//         assertEquals(200, result.order_minimum_surcharge) // The surcharge is the difference between cart value and the minimum threshold
//         assertEquals(cartValue, result.cart_value)
//         assertEquals(100, result.delivery_fee)
//         assertEquals(1000, result.delivery_distance)
//     }

//     // Test with negative cart value to check if exception is thrown
//     @Test
//     fun `should throw exception for negative total price calculation`() {
//         val cartValue = -100
//         val baseDeliveryFee = 100
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(34.0, 136.0)

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         // This should throw an exception due to a negative total price
//         assertFailsWith<Exception> {
//             deliveryFeeTotal.deliveryFeeTotalCalculation()
//         }
//     }

//     // Test with a large cart value to check for correct calculation
//     @Test
//     fun `should handle large cart value correctly`() {
//         val cartValue = 100000
//         val baseDeliveryFee = 500
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(34.0, 136.0)

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

//         // Check that the large cart value is handled properly with no surcharge
//         assertEquals(100500, result.totalPurchasePrice)
//         assertEquals(0, result.order_minimum_surcharge)
//         assertEquals(cartValue, result.cart_value)
//         assertEquals(500, result.delivery_fee)
//         assertEquals(1000, result.delivery_distance)
//     }

//     // Test when there is no distance range match (invalid distance)
//     @Test
//     fun `should throw exception for invalid delivery distance`() {
//         val cartValue = 800
//         val baseDeliveryFee = 100
//         val orderMinimumNoSurcharge = 1000
//         val userCoordinate = listOf(35.0, 135.0)
//         val venueCoordinate = listOf(40.0, 140.0) // A far distance
//         val distanceRanges = listOf(
//             DistanceRange(0, 500, 0, 0.0, null),
//             DistanceRange(500, 1000, 100, 1.0, null)
//         )

//         val deliveryFeeTotal = DeliveryFeeTotal(cartValue, baseDeliveryFee, orderMinimumNoSurcharge, userCoordinate, venueCoordinate, distanceRanges)

//         // No distance range will match because the venue is far away
//         assertFailsWith<BadRequestException> {
//             deliveryFeeTotal.deliveryFeeTotalCalculation()
//         }
//     }
// }
