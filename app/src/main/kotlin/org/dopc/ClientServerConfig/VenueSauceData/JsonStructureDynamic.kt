package dynamixVenueInfoStructure

// For Json decode
import kotlinx.serialization.*
import kotlinx.serialization.json.*
// venue_raw.delivery_specs.delivery_pricing.distance_ranges

@Serializable
data class VenueRawDynamic(
    val venue_raw: DeliverySpecs
)

@Serializable
data class DeliverySpecs(
    val delivery_specs: DeliverySpecsData
)

@Serializable
data class DeliverySpecsData(
    val order_minimum_no_surcharge: Int, 
    val delivery_pricing: DeliveryPricing,
)


@Serializable
data class DeliveryPricing(
    val base_price: Int,
    val distance_ranges: List<DistanceRange>
)


@Serializable
data class DistanceRange(
    val min: Int,
    val max: Int,
    val a: Int,
    val b: Double,
    val flag: Int?
)


