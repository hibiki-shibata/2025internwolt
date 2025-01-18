package distancefeeindex
import dynamixVenueInfoStructure.DistanceRange



class DistanceFee{
    fun distanceFee(baseDeliveryFee: Int, userCoordinate: List<Double>, venueCoordinateList: List<Double>, distanceRanges: List<DistanceRange>): Int {

        val meterStraightDeliveryDistance: Int = calcStraightDeliveryDistance(venueCoordinateList, userCoordinate)           
        // Straiht distance x distanceRange    
        println("STRAIGHT DISTANCE::::::: " + meterStraightDeliveryDistance)

        val a: Int = 1
        val b: Int = 1

        val distanceBasedFee: Int = b * meterStraightDeliveryDistance / 10
        val deliveryPriceDeliveryPriceEstimate: Int = baseDeliveryFee + a + distanceBasedFee
        
        return 121
    }

}