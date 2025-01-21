package org.dopc.clientserverconfig.feecalculation.feecalctotal

import org.dopc.clientserverconfig.venuesaucedata.allvenuedataapi.RequestRestaurantData
import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange
import org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge.MinSurchargeFee
import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFee
import org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex.DistanceFeeInfo


data class CalculatedPricesData(
    val totalPurchasePrice: Int,
    val order_minimum_surcharge: Int,
    val cart_value: Int,
    val delivery_fee: Int,
    val delivery_distance: Int

)

// cartValue: Int, orderMinimumSurcharge: Int, userCoordinates, venuCoordinates, distanceRange, 
internal class DeliveryFeeTotal(
        cart_value: Int,
        base_delivery_fee: Int,
        order_minimum_no_surcharge: Int,
        user_coordinate: List<Double>,
        venue_coordinate: List<Double>,
        distance_ranges: List<DistanceRange>
        ){

    private val cart_value: Int = cart_value
    private val base_delivery_fee: Int = base_delivery_fee
    private val order_minimum_no_surcharge: Int = order_minimum_no_surcharge
    private val user_coordinate: List<Double> = user_coordinate
    private val venue_coordinate: List<Double> = venue_coordinate
    private val distance_ranges: List<DistanceRange> = distance_ranges

    
    internal fun  deliveryFeeTotalCalculation(): CalculatedPricesData {

        // Order size minimum Surcharge
        val finalizedMinSurchargeFee: Int = MinSurchargeFee().minSurchargeFee(cart_value, order_minimum_no_surcharge)
    

        // Distance base pricing
        val distanceFeeResult: DistanceFeeInfo = DistanceFee().distanceFee(base_delivery_fee, user_coordinate, venue_coordinate, distance_ranges)
        val deliveryDistanceMeter: Int = distanceFeeResult.deliveryDistanceInMeter
        val finalizedDistanceFee: Int = distanceFeeResult.distanceFeeTotal
        
        
        // ALL TOTAL PRICE IN THE PUERCHASE!!
        val allTotalPrice: Int = cart_value + finalizedDistanceFee + finalizedMinSurchargeFee 
        
        if(allTotalPrice < 0) throw Exception("Negative total price was calculated:(")

        return CalculatedPricesData(
            totalPurchasePrice = allTotalPrice,
            order_minimum_surcharge = finalizedMinSurchargeFee,
            cart_value = cart_value,
            delivery_fee = finalizedDistanceFee,
            delivery_distance = deliveryDistanceMeter
        )
    }   

}