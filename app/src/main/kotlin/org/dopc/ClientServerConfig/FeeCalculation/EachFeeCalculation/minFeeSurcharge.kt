package org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge



class MinSurchargeFee{

    fun minSurchargeFee(cartValue: Int, minimumOrderThreshold: Int): Int {
            return if (cartValue >= minimumOrderThreshold) 0 else minimumOrderThreshold - cartValue
        }

}