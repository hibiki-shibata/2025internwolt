package org.dopc.clientserverconfig.configclientserver

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
// Json Serialize
import kotlinx.serialization.*
import kotlinx.serialization.json.*

// Http
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException

// For handle status code receive & respond
import io.ktor.server.request.* 
import io.ktor.server.plugins.statuspages.*


import org.dopc.clientserverconfig.indexclientserver.DopcProcessIndex
import org.dopc.clientserverconfig.indexclientserver.ResponseDataToClient



class ClientServer {
    
    fun deliveryOrderFeeCalculator() {
        embeddedServer(Netty, port = 8000) {

            install(ContentNegotiation){
                json(Json{ignoreUnknownKeys = true})
            }

            install(StatusPages) {
                exception<Throwable> { call, cause ->
                    if(cause is BadRequestException) {
                        call.respondText(
                            text = "400: Invalid request\nRequired params: venue_slug(Strong), cart_value(Int), user_lat(Double), user_lon(Double)\n\nDetails:\n${cause.message}",
                            status = HttpStatusCode.BadRequest
                        )
                    } else {
                        call.respondText(
                            text = "500: Internal Server Error\n Unexpected error happened:(",
                            status = HttpStatusCode.InternalServerError
                        )
                    }
                }
            }

            routing {
                get("/api/v1/delivery-order-price") {
                        
                    val responseDataJson: ResponseDataToClient = DopcProcessIndex().dopcIndexCalculation(call)
                                    
                    call.respond(responseDataJson )
                        // if we want to response as a string↓↓↓
                        // val responseDataString: ResponseDataToClient = Json.encodeToString(responseDataJson)        
                }
            }
        }.start(wait = true)
    }

}