package clientserverindex

import io.ktor.server.application.*

import clientreqvalidation.ClientReqDataValidations
import clientreqvalidation.ClientRequestParams

import clientserverindex.DopcProcessIndex

import extractrequireddata.ExtractRequiredVenueInfoForDopc
import extractrequireddata.VenueDynamicData
import extractrequireddata.VenueStaticData

import feecalculationindex.DeliveryFeeTotal

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
        val userCoordinatesList: List<Double> = listOf(clientReqDataValidations.user_lon, clientReqDataValidations.user_lat)

        
        // FetchVenueSauceData
        val venueSauceInfo = ExtractRequiredVenueInfoForDopc(clientReqDataValidations.venue_slug)
        val venueDataStatic: VenueStaticData = venueSauceInfo.venueCoordinatesStatic() // coordinates
        val venueDataDynamic: VenueDynamicData = venueSauceInfo.venueDeliveryFeesDynamic() // order_minimum_no_surcharge, delivery_pricing, distance_ranges
        
        val totalDeliveryPrice = DeliveryFeeTotal(
                clientReqDataValidations.cart_value,
                venueDataDynamic.base_price,
                venueDataDynamic.order_minimum_no_surcharge,
                userCoordinatesList,
                venueDataStatic.coordinates,
                venueDataDynamic.distance_ranges
            ).deliveryFeeTotalCalculation()

            println("Venue Static PRINT::::::: " + venueDataStatic)
            println("Venue Dynamic PRINT::::::: " + venueDataDynamic)


        return clientReqDataValidations
    }

}