package clientserverindex

import io.ktor.server.application.*

import clientreqvalidation.ClientReqDataValidations
import clientreqvalidation.ClientRequestParams



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
    
    fun dopcIndexCalculation(call: ApplicationCall): ClientRequestParams {
        val clientReqDataValidations: ClientRequestParams = ClientReqDataValidations().catchClientReqParams(call)  
        
    return clientReqDataValidations
    }

}