package clientserverindex

import io.ktor.server.application.*

import clientreqvalidation.ClientReqDataValidations
import clientreqvalidation.ClientRequestParams

import clientserverindex.DopcProcessIndex
import extractrequireddata.ExtractRequiredVenueInfoForDopc


data class ResponseDataToClient(
    val total_price: Int,
    val small_order_surcharge: Int,
    val cart_value: Int,
    val delivery: Delivery
  )

  
data class Delivery(
    val fee: Int,
    val distance: Int
)


class DopcProcessIndex {
    
    suspend fun dopcIndexCalculation(call: ApplicationCall): ClientRequestParams {

        // Examine User Info, Check if request URI has required params in expected data type, otherwise throw Badrequest error
        val clientReqDataValidations: ClientRequestParams = ClientReqDataValidations().catchClientReqParams(call) // (venue_slug, cart_value, user_lat,  user_lon:)
        
        // FetchVenueSauceData
        val venueSauceInfo = ExtractRequiredVenueInfoForDopc(clientReqDataValidations.venue_slug)
        val venueDataStatic = venueSauceInfo.venueCoordinatesStatic()
        val venueDataDynamic = venueSauceInfo.venueDeliveryFeesDynamic()
        println("Venue Static PRINT::::::: " + venueDataStatic)
        println("Venue Dynamic PRINT::::::: " + venueDataDynamic)


        return clientReqDataValidations
    }

}