package org.dopc.clientserverconfig.indexclientserver

import io.ktor.server.application.*

// Json
import kotlinx.serialization.*
import kotlinx.serialization.json.*

import kotlinx.coroutines.*

// import clientserverindex.DopcProcessIndex
import org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate.ClientReqDataValidations
import org.dopc.clientserverconfig.clientrequestsorting.clientreqparamvalidate.ClientRequestParams

import org.dopc.clientserverconfig.venuesaucedata.findrequireddata.ExtractRequiredVenueInfoForDopc
import org.dopc.clientserverconfig.venuesaucedata.findrequireddata.VenueDynamicData
import org.dopc.clientserverconfig.venuesaucedata.findrequireddata.VenueStaticData

import org.dopc.clientserverconfig.feecalculation.feecalctotal.DeliveryFeeTotal
import org.dopc.clientserverconfig.feecalculation.feecalctotal.CalculatedPricesData



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
    
   internal suspend fun dopcIndexCalculation(call: ApplicationCall): ResponseDataToClient = runBlocking{

        ///// Extract required Data 
        // Examine User Info, Check if request URI has required params in expected data type, otherwise throw Badrequest error
        val clientReqDataValidations: ClientRequestParams = ClientReqDataValidations().catchClientReqParams(call) // (venue_slug, cart_value, user_coordinate)

        // FetchVenueSauceData
        val venueSauceInfo: ExtractRequiredVenueInfoForDopc = ExtractRequiredVenueInfoForDopc(clientReqDataValidations.venue_slug) // Class Instance
        val venueDataStaticdeffered = async{venueSauceInfo.venueCoordinatesStatic()} // coordinates
        val venueDataDynamicdeffered = async{venueSauceInfo.venueDeliveryFeesDynamic()} // order_minimum_no_surcharge, delivery_pricing, distance_ranges
        
        val venueDataStatic: VenueStaticData = venueDataStaticdeffered.await()
        val venueDataDynamic: VenueDynamicData = venueDataDynamicdeffered.await()


        ///// Fee Calculations
        val deliveryFeeTotal: CalculatedPricesData = DeliveryFeeTotal(
                clientReqDataValidations.cart_value,
                venueDataDynamic.base_price,
                venueDataDynamic.order_minimum_no_surcharge,
                clientReqDataValidations.user_coodinate,
                venueDataStatic.coordinates,
                venueDataDynamic.distance_ranges
            ).deliveryFeeTotalCalculation()



        return@runBlocking ResponseDataToClient(
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