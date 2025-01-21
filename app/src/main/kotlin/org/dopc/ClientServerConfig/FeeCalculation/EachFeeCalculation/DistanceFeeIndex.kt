package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import io.ktor.server.plugins.BadRequestException
import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange

import kotlin.math.roundToInt


data class DistanceFeeInfo(
    val distanceFeeTotal: Int,
    val deliveryDistanceInMeter: Int
)

internal class DistanceFee{
    internal fun distanceFee(baseDeliveryFee: Int, userCoordinate: List<Double>, venueCoordinateList: List<Double>, distanceRanges: List<DistanceRange>): DistanceFeeInfo {

        // Straight Distance User and Venue
        val meterStraightDeliveryDistance: Double = calcStraightDeliveryDistance(venueCoordinateList, userCoordinate)

        // Get distance base pricing logic which is corresponding to the distance between User and Venue.
        val distanceRangePricingConfig: DistanceRange? = findDistanceRange(distanceRanges, meterStraightDeliveryDistance)

        // Check if the user is in valid Delivery Area.
        if(distanceRangePricingConfig == null) throw BadRequestException("DeliveryDistance is Invalid. Your address may not be in delivery area") 
        
        val distanceBasedFee: Int = (distanceRangePricingConfig.b * meterStraightDeliveryDistance / 10).roundToInt() //　Rounded for the nearest integer
        

        // TOTAL "delivery fee" from 1.Base delivery fee, 2.Distance Pricing Fee, 3.Distance pricing base fee
        val distanceFeeTotal: Int = baseDeliveryFee + distanceRangePricingConfig.a + distanceBasedFee
        

        if(distanceFeeTotal < 0) throw Exception("Distance fee was calculated negative")
        
        return DistanceFeeInfo(
             distanceFeeTotal = distanceFeeTotal,
             deliveryDistanceInMeter = meterStraightDeliveryDistance.roundToInt()
        )
    }

}