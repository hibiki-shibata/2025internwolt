package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import kotlin.math.*

internal fun calcStraightDeliveryDistance(venueCoordinates: List<Double>, userCoordinates: List<Double>): Double{

    val lat1Rad: Double = Math.toRadians(venueCoordinates[1])
    val lat2Rad: Double = Math.toRadians(userCoordinates[1])
    val lon1Rad: Double = Math.toRadians(venueCoordinates[0])
    val lon2Rad: Double = Math.toRadians(userCoordinates[0])

    val differenceLat: Double = lat2Rad - lat1Rad
    val differenceLon: Double = lon2Rad - lon1Rad
    val eathRadius: Double = 6371.0 

    val haversineComponent: Double = sin(differenceLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(differenceLon / 2).pow(2)

    val centralAngle: Double = 2 * asin(sqrt(haversineComponent))

    val straightDistanceMeter: Double = eathRadius * centralAngle * 1000

    //Ensure distance is always positive value  
    if (straightDistanceMeter < 0) throw Exception("calculated distance was negative")

return straightDistanceMeter
}
