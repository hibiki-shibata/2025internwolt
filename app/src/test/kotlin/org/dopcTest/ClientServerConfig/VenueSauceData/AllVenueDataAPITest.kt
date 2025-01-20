package org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RequestRestaurantDataTest {

    @Test
    fun `fetchStaticVenueInfo should return response body on successful request`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"

        // Mocked response for a successful request
        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/$venueSlug/static", request.url.toString())
            respond(
                content = """{"data": "Static venue Json"}""",
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val requestRestaurantData = RequestRestaurantData().apply { this.client = client }
        val result = requestRestaurantData.fetchStaticVenueInfo(venueSlug)

        assertEquals("""{"data": "Static venue Json"}""", result)
    }

    
    @Test
    fun `fetchStaticVenueInfo should throw exception on non-successful HTTP status`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"

        // Mocked response for an error status
        val mockEngine = MockEngine {
            respondError(status = HttpStatusCode.InternalServerError)
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val requestRestaurantData = RequestRestaurantData().apply { this.client = client }

        assertFailsWith<Exception>("Client Server failed while fetching venue static data") {
            requestRestaurantData.fetchStaticVenueInfo(venueSlug)
        }
    }


    @Test
    fun `fetchDynamicVenueInfo should return response body on successful request`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"

        // Mocked response for a successful request
        val mockEngine = MockEngine { request ->
            assertEquals("https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/$venueSlug/dynamic", request.url.toString())
            respond(
                content = """{"data": "Dynamic venue Json"}""",
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val requestRestaurantData = RequestRestaurantData().apply { this.client = client }
        val result = requestRestaurantData.fetchDynamicVenueInfo(venueSlug)

        assertEquals("""{"data": "Dynamic venue Json"}""", result)
    }


    @Test
    fun `fetchDynamicVenueInfo should throw exception on non-successful HTTP status`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"

        // Mocked response for an error status
        val mockEngine = MockEngine {
            respondError(status = HttpStatusCode.BadRequest)
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val requestRestaurantData = RequestRestaurantData().apply { this.client = client }

        assertFailsWith<Exception>("Client Server failed while fetching venue dynamic data") {
            requestRestaurantData.fetchDynamicVenueInfo(venueSlug)
        }
    }

    @Test
    fun `should handle invalid URL gracefully`() = runBlocking {
        val venueSlug = "home-assignment-venue-helsinki"

        // Mocked response for an invalid URL
        val mockEngine = MockEngine {
            respondError(status = HttpStatusCode.NotFound)
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val requestRestaurantData = RequestRestaurantData().apply { this.client = client }

        assertFailsWith<Exception>("Client Server failed while fetching venue static data") {
            requestRestaurantData.fetchStaticVenueInfo(venueSlug)
        }
    }
}
