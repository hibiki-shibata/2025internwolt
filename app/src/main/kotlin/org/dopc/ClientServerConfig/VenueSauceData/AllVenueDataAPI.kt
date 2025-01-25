package org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking

// Response status code
import io.ktor.http.*

// Handle Json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

//Time out
import io.ktor.client.plugins.*

import io.ktor.client.call.*



internal class RequestRestaurantData {

   internal var client = HttpClient(CIO){
    install(HttpTimeout){
        requestTimeoutMillis = 5000
    }
   }

    // val venueSlug = "home-assignment-venue-helsinki"
    suspend internal fun fetchStaticVenueInfo(venueSlug: String): String {

        // HttpResponse
        val requestStaticVenueInfo: String = "https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/static"
        val responseStaticVenueInfo: HttpResponse = client.get(requestStaticVenueInfo)

        // Error just in case
        if(responseStaticVenueInfo.status.value !in 200..299) throw Exception("Client Server failed while fetching venue static data") 

        val responseStaticVenueInfoBody: String = responseStaticVenueInfo.body()
        return responseStaticVenueInfoBody
    }


    

    suspend internal fun fetchDynamicVenueInfo(venueSlug: String): String {
    
            // HttpResponse
            val requestDynamicVenueInfo: String = "https://consumer-api.development.dev.woltapi.com/home-assignment-api/v1/venues/${venueSlug}/dynamic"
            val responseDynamicVenueInfo: HttpResponse = client.get(requestDynamicVenueInfo)
            
            // Error just in case
            if(responseDynamicVenueInfo.status.value !in 200..299) throw Exception("Client Server failed while fetching venue dynamic data") 
    
            val responseresponseDynamicVenueInfoBody: String = responseDynamicVenueInfo.body()     
            return responseresponseDynamicVenueInfoBody
        

    }

}



