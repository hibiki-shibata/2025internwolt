package extractrequireddata

import requestvenuedata.RequestRestaurantData

// Json
import kotlinx.serialization.*
import kotlinx.serialization.json.*

import staticVenueInfoStructure.VenueRawStatic
import dynamixVenueInfoStructure.VenueRawDynamic



class ExtractRequiredVenueInfoForDopc(){

    val json = Json { ignoreUnknownKeys = true } // Parse json string
    val requestRestaurantData = RequestRestaurantData()

    suspend fun venueCoordinatesStatic(venueSlug: String){

        // val staticVenueInfo = test.fetchStaticVenueInfo("home-assignment-venue-helsinki")        
        val staticVenueInfo = requestRestaurantData.fetchStaticVenueInfo(venueSlug)
        val staticVenueInfoJson = json.decodeFromString<VenueRawStatic>(staticVenueInfo)
        val venueCoordinates = staticVenueInfoJson.venue_raw.location.coordinates
        
        println("VENUE COORDINATES IS *****:=> "+ venueCoordinates)

        // val dynamicVenueInfo = requestRestaurantData.fetchDynamicVenueInfo(venueSlug)        
    }



    suspend fun venueDeliveryFeesDynamic(venueSlug: String){

        val dynamicVenueInfo = requestRestaurantData.fetchDynamicVenueInfo(venueSlug)
        val dynamicVenueInfoJson = json.decodeFromString<VenueRawDynamic>(dynamicVenueInfo)

        val orderMinimumNoSurcharge = dynamicVenueInfoJson.venue_raw.delivery_specs.order_minimum_no_surcharge
        val basePrice = dynamicVenueInfoJson.venue_raw.delivery_specs.delivery_pricing.base_price
        val distanceRanges = dynamicVenueInfoJson.venue_raw.delivery_specs.delivery_pricing.distance_ranges

        println("SURCHARGE=> "+ orderMinimumNoSurcharge)
        println("BASEPRICE=> " + basePrice)
        println("DISTANCERANGE=> " + distanceRanges)


    }

}