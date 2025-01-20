package org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate

// To Use ApplicationCall Type
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException


data class ClientRequestParams(
    val venue_slug: String,
    val cart_value: Int,
    val user_coodinate: List<Double>
    )
    

class ClientReqDataValidations() { 

    fun catchClientReqParams(call: ApplicationCall): ClientRequestParams {
            
        val venueSlug: String?  = call.request.queryParameters["venue_slug"]?.toString()
        val cartValue: Int? = call.request.queryParameters["cart_value"]?.toIntOrNull()
        val userLat: Double? = call.request.queryParameters["user_lat"]?.toDoubleOrNull()
        val userLon: Double? = call.request.queryParameters["user_lon"]?.toDoubleOrNull()

        
        // Check If URI has all necessary params
        val isValidParams: Boolean = venueSlug !== null && cartValue !== null && userLat !== null && userLon !== null
        if(!isValidParams) throw BadRequestException("invalid params value or missing mandatory parameters" + "slug=" + venueSlug +" cartValue=" + cartValue + " Lat=" + userLat + " Lon=" + userLon)        
        

        // Check if cartValue is negative
        if (cartValue < 0) throw BadRequestException("cartValue must be greater than 0")

        // Check if it's existing coordinates
        if(userLat !in -90.0..90.0) throw BadRequestException("The coordinate doesn't exist in maps")
        if(userLon !in -180.0..180.0) throw BadRequestException("The coordinate doesn't exist in maps")


        // Compile user coordinate in a List
        val userCoordinateList: List<Double> = listOf(userLon, userLat)
        

        return ClientRequestParams(
                venue_slug = venueSlug,
                cart_value = cartValue,
                user_coodinate = userCoordinateList
                )        
    }

}