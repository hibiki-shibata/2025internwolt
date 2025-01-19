package org.dopc.clientserverconfig.venuesaucedata.findrequireddata

// check if expected data appears to the functions
// venueCoordinatesStatic and venueDeliveryFeesDynamic
// maybe we can use one of home-assignment API as an example
import org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi.RequestRestaurantData


import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

import java.nio.file.Files
import java.nio.file.Paths

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExtractRequiredVenueInfoForDopcTest {

    private fun readJsonFromFile(fileName: String): String {
        val uri = this::class.java.getResource("/json/$fileName")?.toURI()
            ?: throw IllegalArgumentException("File $fileName not found.")
        return Files.readString(Paths.get(uri))
    }

///////// Static Venue Info
    @Test
    fun `venueCoordinatesStatic should parse valid static data`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"
        val validStaticJson = readJsonFromFile("valid_static.json")

        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/static", request.url.toString())
            respond(validStaticJson, HttpStatusCode.OK, headersOf("Content-Type", "application/json"))
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val extractor = ExtractRequiredVenueInfoForDopc(venueSlug).apply {
            requestRestaurantData.client = client
        }

        val result = extractor.venueCoordinatesStatic()
        assertEquals(listOf(24.92813512,60.17012143), result.coordinates)
    }

    
    @Test
    fun `Expect IllegalArgumentException for invalid slug name Static`() = runBlocking {
        val venueSlug = "invalid_slug_name"
        val validStaticJson = readJsonFromFile("valid_static.json")

        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/static", request.url.toString())
            respond(validStaticJson, HttpStatusCode.OK, headersOf("Content-Type", "application/json"))
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val extractor = ExtractRequiredVenueInfoForDopc(venueSlug).apply {
            requestRestaurantData.client = client
        }

        assertFailsWith<IllegalArgumentException> {
            extractor.venueCoordinatesStatic()
        }
    }

    

   ///////// Dynamic Venue Info
    @Test
    fun `venueDeliveryFeesDynamic should parse valid dynamic data`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"
        val validDynamicJson = readJsonFromFile("valid_dynamic.json")

        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/dynamic", request.url.toString())
            respond(validDynamicJson, HttpStatusCode.OK, headersOf("Content-Type", "application/json"))
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val extractor = ExtractRequiredVenueInfoForDopc(venueSlug).apply {
            requestRestaurantData.client = client
        }

        val result = extractor.venueDeliveryFeesDynamic()
        assertEquals(1000, result.order_minimum_no_surcharge)
        assertEquals(190, result.base_price)
        // assertEquals(2, result.distance_ranges.size)
    }
    

    
    @Test
    fun `Expect IllegalArgumentException for invalid slug name Dynamic`() = runBlocking {
        val venueSlug = "invalid_slug_name"
        val validDynamicJson = readJsonFromFile("valid_dynamic.json")

        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/dynamic", request.url.toString())
            respond(validDynamicJson, HttpStatusCode.OK, headersOf("Content-Type", "application/json"))
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val extractor = ExtractRequiredVenueInfoForDopc(venueSlug).apply {
            requestRestaurantData.client = client
        }


        assertFailsWith<IllegalArgumentException> {
             extractor.venueDeliveryFeesDynamic()
        }
        
    }
}
