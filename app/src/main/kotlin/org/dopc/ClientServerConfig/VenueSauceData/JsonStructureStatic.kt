package staticVenueInfoStructure

// For Json decode
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class VenueRawStatic(
    val venue_raw: VenueDetails
)

@Serializable
data class VenueDetails(
    val location: Location
)

@Serializable
data class Location(
    val coordinates: List<Double>
)