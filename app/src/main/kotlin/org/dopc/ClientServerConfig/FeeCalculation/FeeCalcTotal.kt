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
        order_minimum_no_surcharge: Int,
        base_delivery_fee: Int,
        user_coordinate: List<Double>,
        venue_coordinate: List<Double>,
        distance_ranges: List<DistanceRange>
        ){

    private val cart_value: Int = cart_value
    private val order_minimum_no_surcharge: Int = order_minimum_no_surcharge
    private val base_delivery_fee: Int = base_delivery_fee
    private val user_coordinate: List<Double> = user_coordinate
    private val venue_coordinate: List<Double> = venue_coordinate
    private val distance_ranges: List<DistanceRange> = distance_ranges

    
    fun  deliveryFeeTotalCalculation(){

        println("ATTENTIIIIIIIIIIION!!")
        println(cart_value)
        println(order_minimum_no_surcharge)
        println(base_delivery_fee)
        println(user_coordinate)
        println(venue_coordinate)
        println(distance_ranges)
        

    }   

}