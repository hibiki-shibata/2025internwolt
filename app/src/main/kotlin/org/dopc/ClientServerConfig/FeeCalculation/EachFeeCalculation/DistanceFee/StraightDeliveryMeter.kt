package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import kotlin.math.*

fun calcStraightDeliveryDistance(venueCoordinates: List<Double>, userCoordinates: List<Double>): Double{

    val lat1Rad: Double = Math.toRadians(venueCoordinates[1])
    val lat2Rad: Double = Math.toRadians(userCoordinates[1])
    val lon1Rad: Double = Math.toRadians(venueCoordinates[0])
    val lon2Rad: Double = Math.toRadians(userCoordinates[0])

    val dLat: Double = lat2Rad - lat1Rad
    val dLon: Double = lon2Rad - lon1Rad
    val radius: Double = 6371.0 

    val a: Double = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)

    val c: Double = 2 * asin(sqrt(a))

    val straightDistanceMeter: Double = radius * c * 1000

    //Ensure distance is always positive value  
    if (straightDistanceMeter < 0) throw Exception("calculated distance was negative")

return straightDistanceMeter
}
