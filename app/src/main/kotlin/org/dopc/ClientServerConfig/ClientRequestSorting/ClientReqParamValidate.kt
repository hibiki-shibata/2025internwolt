package clientreqvalidation

// TO Use ApplicationCall Type
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

        val isValidParams: Boolean = venueSlug !== null && cartValue !== null && userLat !== null && userLon !== null
        
        // Check If URI has all necessary params
        if(!isValidParams) throw BadRequestException("invalid params value or missing mandatory parameters")        
        // Check if cartValue is negative
        if (cartValue < 0) throw BadRequestException("cartValue must be greater than 0")

        // Comile user coordinate in a List
        val userCoordinateList: List<Double> = listOf(userLon, userLat)
        

        return ClientRequestParams(
                venue_slug = venueSlug,
                cart_value = cartValue,
                user_coodinate = userCoordinateList
                )        
    }

}