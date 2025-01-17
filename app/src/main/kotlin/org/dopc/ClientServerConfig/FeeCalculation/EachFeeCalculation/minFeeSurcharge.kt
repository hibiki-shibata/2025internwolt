package minfeesurcharge


// import fetchvenuedata



fun minSurchargeFee(cartValue: Int, minimumOrderThreshold: Int): Int {
        return if (cartValue >= minimumOrderThreshold) 0 else minimumOrderThreshold - cartValue
    }
