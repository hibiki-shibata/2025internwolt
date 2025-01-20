/*
 * This source file was generated by the Gradle 'init' task
 */

// package index

// This is simple code as entry point. 

//Test includes in  Manual Integration Tests


import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import io.ktor.server.plugins.BadRequestException

// to use body()
import io.ktor.client.call.*


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

import java.nio.file.Files
import java.nio.file.Paths


class TotalIntegrationTest {

    val client = HttpClient(CIO) 

    @Test
    fun `should calculate correctly, when cart value is higher than min basket size feet`() = runBlocking {
        val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=2000&user_lat=60.17094&user_lon=24.93087") 
        val responseBody: String = response.body() 

        val exampleResponseJson: String = """{"total_price":2190,"small_order_surcharge":0,"cart_value":2000,"delivery":{"fee":190,"distance":177}}"""
        assertEquals(exampleResponseJson, responseBody)
    }


    @Test
    fun `should calculate correctly, when cart value is the SAME as min basket size fee`() = runBlocking {
        val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=1000&user_lat=60.17094&user_lon=24.93087") 
        val responseBody: String = response.body() 

        val exampleResponseJson: String = """{"total_price":1190,"small_order_surcharge":0,"cart_value":1000,"delivery":{"fee":190,"distance":177}}"""
        assertEquals(exampleResponseJson, responseBody)
    }


    @Test
    fun `should calculate correctly, when cart value is lower than min basket seiz fee`() = runBlocking {
        val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900&user_lat=60.17094&user_lon=24.93087") 
        val responseBody: String = response.body() 

        val exampleResponseJson: String = """{"total_price":1190,"small_order_surcharge":100,"cart_value":900,"delivery":{"fee":190,"distance":177}}"""
        assertEquals(exampleResponseJson, responseBody)
    }


    @Test
    fun `should calculate correctly, when cart value is Zero`() = runBlocking {
        val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=0&user_lat=60.17094&user_lon=24.93087") 
        val responseBody: String = response.body() 

        val exampleResponseJson: String = """{"total_price":1190,"small_order_surcharge":1000,"cart_value":0,"delivery":{"fee":190,"distance":177}}"""
        assertEquals(exampleResponseJson, responseBody)
    }


    @Test
    fun `should calculate correctly, when User coordinate and Venue coordinates are the same`() = runBlocking {
        val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=0&user_lat=60.17012143&user_lon=24.92813512") 
        val responseBody: String = response.body() 

        val exampleResponseJson: String = """{"total_price":1190,"small_order_surcharge":1000,"cart_value":0,"delivery":{"fee":190,"distance":0}}"""
        assertEquals(exampleResponseJson, responseBody)
    }


    @Test
    fun `should return 400 badRequest, when cart value is minus`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900&user_lat=60.17094&user_lon=24.93087")      
        }
    }

    
    @Test
    fun `should return 400 badRequest, when user coordinate is outside of delivery area`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900&user_lat=600.17094&user_lon=240.93087")      
        }
    }


    @Test
    fun `should return 400 badRequest, venue_slug is invalid data type`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=100&cart_value=900&user_lat=60.17094&user_lon=24.93087")      
        }
    }


    @Test
    fun `should return 400 badRequest, cart_value is invalid data type`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900.0&user_lat=60.17094&user_lon=24.93087")      
        }
    } 

    @Test
    fun `should return 400 badRequest, user_lat is invalid data type`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900&user_lat=60&user_lon=24.93087")      
        }
    } 


    @Test
    fun `should return 400 badRequest, user_lon is invalid data type`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=900&user_lat=60.17094&user_lon=24")  
        }
    } 

    
    @Test
    fun `should return 400 badRequest, value of venue_slug key doesn't exists`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=invalddsdid&cart_value=2000&user_lat=60.17094&user_lon=24.93087") 
        }
    } 

    @Test
    fun `should return 400 badRequest, venue_slug is missing in parameters`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?cart_value=2000&user_lat=60.17094&user_lon=24.93087") 
        }
    } 

    @Test
    fun `should return 400 badRequest, cart_value is missing in parameters`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&user_lat=60.17094&user_lon=24.93087") 
        }
    }

    @Test
    fun `should return 400 badRequest, user_lat is missing in parameters`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=2000&user_lon=24.93087")
        }
    } 

    @Test
    fun `should return 400 badRequest, user_lon is missing in parameters`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get(  "http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=2000&user_lat=60.17094")
        }
    } 

    @Test
    fun `should return 400 badRequest, when user coordinate doesn't exist on earth`() = runBlocking {
        assertFailsWith<BadRequestException> {
            val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=2000&user_lat=500.17094&user_lon=24.93087")
        }
    } 

    
}

