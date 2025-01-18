package feecalculationindex

import requestvenuedata.RequestRestaurantData
import dynamixVenueInfoStructure.DistanceRange
// import extractrequireddata.VenueStaticData
// import minfeesurcharge.minFreeSurcharge() // Function

// data class cartValue(
//     val cart_value: Int,
//     val order_minimum_no_surcharge: Int,
//     val user_coordinate: List<Double>
//     val venue_coordinate: List<Double>// VenueStaticData
// )

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

    
    fun  deliveryFeeTotalCalculation(): Int {

        println("ATTENTIIIIIIIIIIION!!")
        println("cart value" + cart_value)
        println("base delivery fee" + base_delivery_fee)
        println("minimu no surcharge" + order_minimum_no_surcharge)
        println("user coordinate" + user_coordinate)
        println("venue coordinate" + venue_coordinate)
        println("distance range" + distance_ranges)

        val finalizedSurchargeFee: Int = minSurchargeFee(cart_value, order_minimum_no_surcharge)
        println("finalizedSurchargeFee: " + finalizedSurchargeFee)
        
        return 11
    }   

}