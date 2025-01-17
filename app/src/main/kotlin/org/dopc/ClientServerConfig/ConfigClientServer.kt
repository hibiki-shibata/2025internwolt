package clientserverconfig

// Ktor Server
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable

// Json
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Http
import io.ktor.http.HttpStatusCode

import io.ktor.server.plugins.BadRequestException

// For handle status code receive & respond
import io.ktor.server.request.* 

import clientserverindex.DopcProcessIndex

class ClientServer {
    
    fun deliveryOrderFeeCalculator() {
        embeddedServer(Netty, port = 8000) {

            install(ContentNegotiation){
                json(Json{ignoreUnknownKeys = false})
            }

            routing {
                get("/api/v1/delivery-order-price") {
                    try {
                        
                        val responseData: Any = DopcProcessIndex().dopcIndexCalculation(call)
                        // Extract required valid params values
                        // val clientReqDataValidations: ClientRequestParams = ClientReqDataValidations().catchClientReqParams(call)  
             
                        call.respond("Response chu: " + responseData )
                   
                                            
                    } catch (e: BadRequestException) {                   
                        call.respond(HttpStatusCode.BadRequest, " 400: Invalid request\nRequired params: venue_slug(Strong), cart_value(Int), user_lat(Double), user_lon(Double)\n${e.message}")

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "500: Internal Server Error\n Unexpected error happened:(")
                    }

                }
            }
        }.start(wait = true)
    }

}