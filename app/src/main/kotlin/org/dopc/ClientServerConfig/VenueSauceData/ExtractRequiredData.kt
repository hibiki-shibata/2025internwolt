package extractrequireddata

import requestvenuedata.RequestRestaurantData

// Json
import kotlinx.serialization.*
import kotlinx.serialization.json.*

import staticVenueInfoStructure.VenueRawStatic
import dynamixVenueInfoStructure.VenueRawDynamic
import dynamixVenueInfoStructure.DistanceRange


data class VenueStaticData (
    val coordinates: List<Double>
)


data class VenueDynamicData (
    val order_minimum_no_surcharge: Int,
    val delivery_pricing: Int,
    val distance_ranges: List<DistanceRange>
)



class ExtractRequiredVenueInfoForDopc(venueSlug: String) {

    private val venueSlug: String = venueSlug

    val json = Json { ignoreUnknownKeys = true } // Parse json string
    val requestRestaurantData = RequestRestaurantData()

    suspend fun venueCoordinatesStatic(): VenueStaticData {

        val staticVenueJsonString: String = requestRestaurantData.fetchStaticVenueInfo(venueSlug)
        val staticVenueInfoJson = json.decodeFromString<VenueRawStatic>(staticVenueJsonString)

        val venueCoordinates: List<Double> = staticVenueInfoJson.venue_raw.location.coordinates
        // val staticVenueInfo = test.fetchStaticVenueInfo("home-assignment-venue-helsinki")        

        return VenueStaticData(
            coordinates = venueCoordinates
            )   
    }




    suspend fun venueDeliveryFeesDynamic(): VenueDynamicData {

        val dynamicVenueJsonString: String = requestRestaurantData.fetchDynamicVenueInfo(venueSlug)
        val dynamicVenueInfoJson = json.decodeFromString<VenueRawDynamic>(dynamicVenueJsonString)

        val orderMinimumNoSurcharge: Int = dynamicVenueInfoJson.venue_raw.delivery_specs.order_minimum_no_surcharge
        val basePrice: Int = dynamicVenueInfoJson.venue_raw.delivery_specs.delivery_pricing.base_price
        val distanceRanges: List<DistanceRange> = dynamicVenueInfoJson.venue_raw.delivery_specs.delivery_pricing.distance_ranges


        return VenueDynamicData(
            order_minimum_no_surcharge = orderMinimumNoSurcharge,
            delivery_pricing = basePrice,
            distance_ranges = distanceRanges
        )
    }

}