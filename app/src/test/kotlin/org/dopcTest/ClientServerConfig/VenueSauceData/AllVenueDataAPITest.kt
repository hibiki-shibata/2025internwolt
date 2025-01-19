// package org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi

// import io.ktor.client.*
// import io.ktor.client.engine.mock.*
// import io.ktor.client.plugins.contentnegotiation.*
// import io.ktor.http.*
// import io.ktor.serialization.kotlinx.json.*
// import kotlinx.coroutines.runBlocking
// import kotlinx.serialization.json.Json
// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertFailsWith

// @Test
// fun `deliveryFeeTotalCalculation should calculate correct values for the given input`() {
//     // Mock MinSurchargeFee
//     mockkConstructor(MinSurchargeFee::class)
//     every { anyConstructed<MinSurchargeFee>().minSurchargeFee(1000, 1000) } returns 0

//     // Mock DistanceFee
//     mockkConstructor(DistanceFee::class)
//     val mockDistanceFeeInfo = DistanceFeeInfo(
//         distanceFeeTotal = 190,
//         deliveryDistanceInMeter = 177
//     )
//     every {
//         anyConstructed<DistanceFee>().distanceFee(
//             baseDeliveryFee = 400, // Example base fee
//             userCoordinate = listOf(60.17094, 24.93087),
//             venueCoordinate = listOf(60.1708, 24.9375), // Assume venue coordinate
//             distanceRanges = any()
//         )
//     } returns mockDistanceFeeInfo

//     // Input values
//     val cartValue = 1000
//     val baseDeliveryFee = 400
//     val orderMinimumNoSurcharge = 1000
//     val userCoordinate = listOf(60.17094, 24.93087)
//     val venueCoordinate = listOf(60.1708, 24.9375)
//     val distanceRanges = listOf(
//         DistanceRange(minDistance = 0, maxDistance = 200, fee = 190),
//         DistanceRange(minDistance = 201, maxDistance = 1000, fee = 300)
//     )

//     // Create the DeliveryFeeTotal instance
//     val deliveryFeeTotal = DeliveryFeeTotal(
//         cart_value = cartValue,
//         base_delivery_fee = baseDeliveryFee,
//         order_minimum_no_surcharge = orderMinimumNoSurcharge,
//         user_coordinate = userCoordinate,
//         venue_coordinate = venueCoordinate,
//         distance_ranges = distanceRanges
//     )

//     // Calculate the result
//     val result = deliveryFeeTotal.deliveryFeeTotalCalculation()

//     // Verify the result
//     val expected = CalculatedPricesData(
//         totalPurchasePrice = 1190,
//         order_minimum_surcharge = 0,
//         cart_value = 1000,
//         delivery_fee = 190,
//         delivery_distance = 177
//     )
//     assertEquals(expected, result)

//     // Verify mock interactions
//     verify { anyConstructed<MinSurchargeFee>().minSurchargeFee(1000, 1000) }
//     verify {
//         anyConstructed<DistanceFee>().distanceFee(
//             baseDeliveryFee = 400,
//             userCoordinate = userCoordinate,
//             venueCoordinate = venueCoordinate,
//             distanceRanges = distanceRanges
//         )
//     }

//     // Clear mocks
//     unmockkAll()
// }


//error when inexisting venueSlug