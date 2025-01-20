// package org.dopc.clientserverconfig.indexclientserver

// This index class is the main steam of processes of server. Response Data is only reply on this function. 
// So the Test includes in Manual Integration Tests for check final responses.


import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*


// to use body()
import io.ktor.client.call.*


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


class TotalIntegrationTest {

    val client = HttpClient(CIO) 
    @Test
    fun `test code anyway`() = runBlocking {
    val response: HttpResponse = client.get("http://localhost:8000/api/v1/delivery-order-price?venue_slug=home-assignment-venue-helsinki&cart_value=1000&user_lat=60.17094&user_lon=24.93087") 
    val something: String = response.body() 
    println(something)
    println("HIBIKIIIIIII::::::::::::::")
    }

}