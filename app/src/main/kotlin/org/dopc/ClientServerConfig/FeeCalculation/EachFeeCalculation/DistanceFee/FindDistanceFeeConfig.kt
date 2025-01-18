package distancefeeindex

import dynamixVenueInfoStructure.DistanceRange


fun findDistanceRange(distanceRanges: List<DistanceRange>, deliveryDistance: Double): DistanceRange? {
    return distanceRanges.find { range: DistanceRange  ->
        // Check if the distance is within the range
        deliveryDistance >= range.min && (range.max == 0 || deliveryDistance < range.max)
    }
}