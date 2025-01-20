// package org.dopc.clientserverconfig.configclientserver

// import io.ktor.client.request.*
// import io.ktor.client.statement.*
// import io.ktor.http.*
// import io.ktor.server.testing.*
// import io.ktor.server.application.*
// import io.ktor.server.routing.*
// import io.ktor.serialization.kotlinx.json.*
// import kotlinx.serialization.json.Json
// import io.ktor.server.plugins.contentnegotiation.*
// import kotlinx.coroutines.runBlocking




// import org.dopc.clientserverconfig.indexclientserver.DopcProcessIndex
// import org.dopc.clientserverconfig.indexclientserver.ResponseDataToClient
// import org.dopc.clientserverconfig.indexclientserver.Delivery

// // TEST
// import kotlin.test.Test
// import kotlin.test.assertEquals
// import io.mockk.mockk
// import io.mockk.every
// import io.mockk.coEvery

// class ClientServerTest {

//     @Test
//     fun `test deliveryOrderFeeCalculator returns correct response`() = testApplication {
//         // Setup the application with the deliveryOrderFeeCalculator route
//         application {
//             install(ContentNegotiation) {
//                 json(Json { ignoreUnknownKeys = false }) // Configure JSON parsing
//             }
            
//             routing {
//                 // Here, you define the route you want to test
//                 get("/api/v1/delivery-order-price") {
//                     // Simulate the logic you expect for this route
//                     try {
//                         // Mock DopcProcessIndex and its method
//                         val mockDopcProcessIndex: DopcProcessIndex = mockk()
                        
//                         val delivery = Delivery(fee = 190, distance = 177)
//                         val mockResponseData = ResponseDataToClient(
//                             total_price = 1190,
//                             small_order_surcharge = 0,
//                             cart_value = 1000,
//                             delivery = delivery
//                         )
                        
                        
//                         coEvery { mockDopcProcessIndex.dopcIndexCalculation(any()) } returns mockResponseData
                    
                        
//                         val responseDataJsonString = runBlocking {
//                             mockDopcProcessIndex.dopcIndexCalculation(mockk())
//                         }
//                         val responseDataJson: String = Json.encodeToString(responseDataJsonString)

//                         call.respond(HttpStatusCode.OK, responseDataJson)
//                     } catch (e: Exception) {
//                         call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unknown error")
//                     }
//                 }
//             }
//         }

//         // Send a request to the embedded server
//         val response = client.get("/api/v1/delivery-order-price") {
//             url {
//                 protocol = URLProtocol.HTTP
//                 host = "localhost"
//                 port = 8000
//                 parameters.append("venue_slug", "home-assignment-venue-helsinki")
//                 parameters.append("cart_value", "1000")
//                 parameters.append("user_lat", "60.17094")
//                 parameters.append("user_lon", "24.93087")
//             }
//         }

//         // Assert the response is as expected
//         assertEquals(HttpStatusCode.OK, response.status)
//         // You can assert the content based on the expected response
//         // For example, check if the response body is of the expected type or contains certain data
//     }
// }
