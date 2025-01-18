package distancefeeindex
// import dynamixVenueInfoStructure.DistanceRange
import kotlin.math.*
// import kotlin.math.roundToInt


fun calcStraightDeliveryDistance(venueCoordinates: List<Double>, userCoordinates: List<Double>): Double{
    // val diffeLon: Double = venueCoordinates[0] - userCoordinates[0]
    // val diffeLat: Double = venueCoordinates[1] - userCoordinates[1]

    val lat1Rad = Math.toRadians(venueCoordinates[1])
    val lat2Rad = Math.toRadians(userCoordinates[1])
    val lon1Rad = Math.toRadians(venueCoordinates[0])
    val lon2Rad = Math.toRadians(userCoordinates[0])

    val dLat = lat2Rad - lat1Rad
    val dLon = lon2Rad - lon1Rad
    val radius = 6371.0 

    val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)

    val c = 2 * asin(sqrt(a))

    val straightDistanceMeter: Double = radius * c * 1000

return straightDistanceMeter
}
