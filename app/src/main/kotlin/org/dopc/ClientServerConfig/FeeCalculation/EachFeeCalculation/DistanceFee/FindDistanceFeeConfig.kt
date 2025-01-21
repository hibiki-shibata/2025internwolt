package org.dopc.clientserverconfig.feecalculation.eachcalculation.distancefeeindex

import org.dopc.clientserverconfig.venuesaucedata.jsonstructuredynamic.DistanceRange


internal fun findDistanceRange(distanceRanges: List<DistanceRange>, deliveryDistance: Double): DistanceRange? {
    
    for (range in distanceRanges) {
        if (deliveryDistance >= range.min && deliveryDistance < range.max) return range        
    }    
    return null // Expect return null, when max = 0 too

}