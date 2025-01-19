package org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge



class MinSurchargeFee{

    fun minSurchargeFee(cartValue: Int, minimumOrderThreshold: Int): Int {
        
        if (cartValue < 0 || minimumOrderThreshold < 0) throw Exception("Negative values were tried be used in order surcharge")
        return if (cartValue >= minimumOrderThreshold) 0 else minimumOrderThreshold - cartValue
        
    }

}