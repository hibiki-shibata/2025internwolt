package clientreqvalidation

// TO Use ApplicationCall Type
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException

data class ClientRequestParams(
    val venue_slug: String,
    val cart_value: Int,
    val user_lat: Double,
    val user_lon: Double
    )

class ClientReqDataValidations() { 

    fun catchClientReqParams(call: ApplicationCall): ClientRequestParams {
        val venueSlug = call.request.queryParameters["venue_slug"]?.toString()
        val cartValue = call.request.queryParameters["cart_value"]?.toIntOrNull()
        val userLat = call.request.queryParameters["user_lat"]?.toDoubleOrNull()
        val userLon = call.request.queryParameters["user_lon"]?.toDoubleOrNull()

        val isValidParams: Boolean = venueSlug !== null && cartValue !== null && userLat !== null && userLon !== null
        
        // Check If URI has all necessary params
        if(!isValidParams) throw BadRequestException("invalid params value or missing mandatory parameters")        
        // Check if cartValue is negative
        if (cartValue < 0) throw BadRequestException("cartValue must be greater than 0")
        

        return ClientRequestParams(
                venue_slug = venueSlug,
                cart_value = cartValue,
                user_lat = userLat,
                user_lon = userLon
                )        
    }

}