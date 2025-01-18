package clientserverindex

import io.ktor.server.application.*

import clientreqvalidation.ClientReqDataValidations
import clientreqvalidation.ClientRequestParams

import clientserverindex.DopcProcessIndex

import extractrequireddata.ExtractRequiredVenueInfoForDopc
import extractrequireddata.VenueDynamicData
import extractrequireddata.VenueStaticData

import feecalculationindex.DeliveryFeeTotal
import feecalculationindex.CalculatedPricesData

// Json
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class ResponseDataToClient(
    val total_price: Int,
    val small_order_surcharge: Int,
    val cart_value: Int,
    val delivery: Delivery
  )

@Serializable
data class Delivery(
    val fee: Int,
    val distance: Int
)


class DopcProcessIndex {
    
    suspend fun dopcIndexCalculation(call: ApplicationCall): ResponseDataToClient {

        ///// Extract required Data 
        // Examine User Info, Check if request URI has required params in expected data type, otherwise throw Badrequest error
        val clientReqDataValidations: ClientRequestParams = ClientReqDataValidations().catchClientReqParams(call) // (venue_slug, cart_value, user_lat,  user_lon:)
        val userCoordinatesList: List<Double> = listOf(clientReqDataValidations.user_lon, clientReqDataValidations.user_lat)

        // FetchVenueSauceData
        val venueSauceInfo: ExtractRequiredVenueInfoForDopc = ExtractRequiredVenueInfoForDopc(clientReqDataValidations.venue_slug) // Class Instance
        val venueDataStatic: VenueStaticData = venueSauceInfo.venueCoordinatesStatic() // coordinates
        val venueDataDynamic: VenueDynamicData = venueSauceInfo.venueDeliveryFeesDynamic() // order_minimum_no_surcharge, delivery_pricing, distance_ranges
        
///// Fee Calculations
        val deliveryFeeTotal: CalculatedPricesData = DeliveryFeeTotal(
                clientReqDataValidations.cart_value,
                venueDataDynamic.base_price,
                venueDataDynamic.order_minimum_no_surcharge,
                userCoordinatesList,
                venueDataStatic.coordinates,
                venueDataDynamic.distance_ranges
            ).deliveryFeeTotalCalculation()

            

        return ResponseDataToClient(
             total_price = deliveryFeeTotal.totalPurchasePrice,
             small_order_surcharge = deliveryFeeTotal.order_minimum_surcharge,
             cart_value = deliveryFeeTotal.cart_value,
             delivery = Delivery (
                fee = deliveryFeeTotal.delivery_fee,
                distance = deliveryFeeTotal.delivery_distance            
             )
        )
    }

}