
// package org.dopc.clientserverconfig.indexclientserver


// // Enry point


// import io.ktor.server.application.*
// import io.ktor.server.request.ApplicationRequest
// import io.ktor.http.Parameters
// import io.mockk.*
// import io.ktor.server.application.ApplicationCall


// import kotlinx.coroutines.runBlocking
// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertFailsWith


// import org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate.*
// // import org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate.ClientReqDataValidations
// import org.dopc.clientserverconfig.venuesaucedata.findrequireddata.*
// // import org.dopc.clientserverconfig.venuesaucedata.findrequireddata.ExtractRequiredVenueInfoForDopc
// import org.dopc.clientserverconfig.feecalculation.feecalctotal.*
// // import org.dopc.clientserverconfig.feecalculation.feecalctotal.DeliveryFeeTotal
// import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange

// class DopcProcessIndexTest {

//     @Test
//     fun `should calculate total price successfully`() = runBlocking {
//         // Mock dependencies
//         val mockClientReqDataValidations = mockk<ClientReqDataValidations>()
//         val mockExtractRequiredVenueInfo = mockk<ExtractRequiredVenueInfoForDopc>()
//         val mockDeliveryFeeTotal = mockk<DeliveryFeeTotal>()

//         val applicationCall = mockk<ApplicationCall>()
//         val mockRequest = mockk<ApplicationRequest>()

//         // Mock the queryParameters to return the required parameters
//         every { applicationCall.request } returns mockRequest
//         every { mockRequest.queryParameters } returns Parameters.build {
//             append("venue_slug", "home-assignment-venue-helsinki")
//             append("cart_value", "1000")
//             append("user_lat", "60.17094")
//             append("user_lon", "24.93087")
// }
        
//         println("Mocked Parameters: ${mockRequest.queryParameters}")

//         // Stub methods
//         every { mockClientReqDataValidations.catchClientReqParams(applicationCall) } returns ClientRequestParams(
//             venue_slug = "home-assignment-venue-helsinki",
//             cart_value = 1000,
//             user_coodinate = listOf(24.93087, 60.17094)
//         )

//         coEvery { mockExtractRequiredVenueInfo.venueCoordinatesStatic() } returns VenueStaticData(
//             coordinates = listOf(24.92813512,60.17012143)
//         )

//         coEvery { mockExtractRequiredVenueInfo.venueDeliveryFeesDynamic() } returns VenueDynamicData(
//             order_minimum_no_surcharge = 1000,
//             base_price = 190,
//             distance_ranges = listOf(
//                 DistanceRange(min = 0, max = 500, a = 0, b = 0.0, flag = null),
//                 DistanceRange(min = 500, max = 1000, a = 100, b = 0.0, flag = null),
//                 DistanceRange(min = 1000, max = 1500, a = 200, b = 0.0, flag = null),
//                 DistanceRange(min = 1500, max = 2000, a = 200, b = 1.0, flag = null),
//                 DistanceRange(min = 2000, max = 0, a = 0, b = 0.0, flag = null)
//             )
//         )

//         every { mockDeliveryFeeTotal.deliveryFeeTotalCalculation() } returns CalculatedPricesData(
//             totalPurchasePrice = 1190,
//             order_minimum_surcharge = 0,
//             cart_value = 1000,
//             delivery_fee = 190,
//             delivery_distance = 177
//         )

//         // Instance of the class under test
//         val dopcProcessIndex = DopcProcessIndex()

//         // Run the function
//         val response = dopcProcessIndex.dopcIndexCalculation(applicationCall)

//         // Assertions
//         assertEquals(1190, response.total_price)
//         assertEquals(0, response.small_order_surcharge)
//         assertEquals(1000, response.cart_value)
//         assertEquals(190, response.delivery.fee)
//         assertEquals(177, response.delivery.distance)

//         val result = mockClientReqDataValidations.catchClientReqParams(applicationCall)
//         assertEquals("home-assignment-venue-helsinki", result.venue_slug)
//         assertEquals(1000, result.cart_value)
//         assertEquals(listOf(24.93087, 60.17094), result.user_coodinate)

//         // Verify interactions
//         coVerify { mockExtractRequiredVenueInfo.venueCoordinatesStatic() }
//         coVerify { mockExtractRequiredVenueInfo.venueDeliveryFeesDynamic() }
//         verify { mockDeliveryFeeTotal.deliveryFeeTotalCalculation() }
//     }







//     @Test
//     fun `should throw error if required params are missing`() = runBlocking {
//         // Mock dependencies
//         val mockClientReqDataValidations = mockk<ClientReqDataValidations>()
//         val applicationCall = mockk<ApplicationCall>()

//         // Stub methods to throw an exception
//         every { mockClientReqDataValidations.catchClientReqParams(applicationCall) } throws IllegalArgumentException("Missing required parameters")

//         // Instance of the class under test
//         val dopcProcessIndex = DopcProcessIndex()

//         // Run the function and assert exception
//         val exception = assertFailsWith<IllegalArgumentException> {
//             dopcProcessIndex.dopcIndexCalculation(applicationCall)
//         }

//         assertEquals("Missing required parameters", exception.message)
//     }

//     @Test
//     fun `should throw error if fetching static venue data fails`() = runBlocking {
//         // Mock dependencies
//         val mockExtractRequiredVenueInfo = mockk<ExtractRequiredVenueInfoForDopc>()
//         val mockClientReqDataValidations = mockk<ClientReqDataValidations>()
//         val applicationCall = mockk<ApplicationCall>()

//         // Stub methods
//         every { mockClientReqDataValidations.catchClientReqParams(applicationCall) } returns ClientRequestParams(
//             venue_slug = "test-venue",
//             cart_value = 500,
//             user_coodinate = listOf(60.1699, 24.9384)
//         )

//         coEvery { mockExtractRequiredVenueInfo.venueCoordinatesStatic() } throws IllegalArgumentException("Static venue data not found")

//         // Instance of the class under test
//         val dopcProcessIndex = DopcProcessIndex()

//         // Run the function and assert exception
//         val exception = assertFailsWith<IllegalArgumentException> {
//             dopcProcessIndex.dopcIndexCalculation(applicationCall)
//         }

//         assertEquals("Static venue data not found", exception.message)
//     }

//     @Test
//     fun `should throw error if delivery fee calculation fails`() = runBlocking {
//         // Mock dependencies
//         val mockExtractRequiredVenueInfo = mockk<ExtractRequiredVenueInfoForDopc>()
//         val mockClientReqDataValidations = mockk<ClientReqDataValidations>()
//         val mockDeliveryFeeTotal = mockk<DeliveryFeeTotal>()
//         val applicationCall = mockk<ApplicationCall>()

//         // Stub methods
//         every { mockClientReqDataValidations.catchClientReqParams(applicationCall) } returns ClientRequestParams(
//             venue_slug = "test-venue",
//             cart_value = 500,
//             user_coodinate = listOf(60.1699, 24.9384)
//         )

//         coEvery { mockExtractRequiredVenueInfo.venueCoordinatesStatic() } returns VenueStaticData(
//             coordinates = listOf(60.192059, 24.945831)
//         )

//         coEvery { mockExtractRequiredVenueInfo.venueDeliveryFeesDynamic() } returns VenueDynamicData(
//             order_minimum_no_surcharge = 1000,
//             base_price = 200,
//             distance_ranges = listOf()
//         )

//         every { mockDeliveryFeeTotal.deliveryFeeTotalCalculation() } throws IllegalArgumentException("Negative total price was calculated")

//         // Instance of the class under test
//         val dopcProcessIndex = DopcProcessIndex()

//         // Run the function and assert exception
//         val exception = assertFailsWith<IllegalArgumentException> {
//             dopcProcessIndex.dopcIndexCalculation(applicationCall)
//         }

//         assertEquals("Negative total price was calculated", exception.message)
//     }
// }
