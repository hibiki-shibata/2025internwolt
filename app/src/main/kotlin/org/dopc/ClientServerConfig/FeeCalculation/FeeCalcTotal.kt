package feecalculationindex

import requestvenuedata.RequestRestaurantData
import dynamixVenueInfoStructure.DistanceRange
// import extractrequireddata.VenueStaticData
// import minfeesurcharge.minFreeSurcharge() // Function
import minsurchargefee.MinSurchargeFee

import distancefeeindex.DistanceFee
import distancefeeindex.DistanceFeeInfo


data class CalculatedPricesData(
    val totalPurchasePrice: Int,
    val order_minimum_surcharge: Int,
    val cart_value: Int,
    val delivery_fee: Int,
    val delivery_distance: Int

)

// cartValue: Int, orderMinimumSurcharge: Int, userCoordinates, venuCoordinates, distanceRange, 
class DeliveryFeeTotal(
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

    
    fun  deliveryFeeTotalCalculation(): CalculatedPricesData {

        // Order size minimum Surcharge
        val finalizedMinSurchargeFee: Int = MinSurchargeFee().minSurchargeFee(cart_value, order_minimum_no_surcharge)
    

        // Distance base pricing
        val distanceFeeResult: DistanceFeeInfo = DistanceFee().distanceFee(base_delivery_fee, user_coordinate, venue_coordinate, distance_ranges)
        val deliveryDistanceMeter: Int = distanceFeeResult.deliveryDistanceInMeter
        val finalizedDistanceFee: Int = distanceFeeResult.distanceFeeTotal
        
        
        // ALL TOTAL PRICE IN THE PUERCHASE!!
        val allTotalPrice: Int = cart_value + finalizedDistanceFee + finalizedMinSurchargeFee 
        
        return CalculatedPricesData(
            totalPurchasePrice = allTotalPrice,
            order_minimum_surcharge = finalizedMinSurchargeFee,
            cart_value = cart_value,
            delivery_fee = finalizedDistanceFee,
            delivery_distance = deliveryDistanceMeter
        )
    }   

}