package org.dopc.clientserverconfig.feecalculation.eachcalculation.minfeesurcharge


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

import kotlinx.coroutines.*


class MinSurchargeFeeTest {

    private val minSurchargeFee = MinSurchargeFee()

    // Test when cart value is above the minimum order threshold
    @Test
    fun `should return 0 when cart value is above minimum threshold`() {
        val cartValue = 1200
        val minimumOrderThreshold = 1000
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        
        assertEquals(0, result)
    }

    // Test when cart value is equal to the minimum order threshold
    @Test
    fun `should return 0 when cart value equals minimum threshold`() {
        val cartValue = 1000
        val minimumOrderThreshold = 1000
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
    
        assertEquals(0, result)
    }

    // Test when cart value is below the minimum order threshold
    @Test
    fun `should return surcharge when cart value is below minimum threshold`() {
        val cartValue = 800
        val minimumOrderThreshold = 1000
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        
        assertEquals(200, result)
    }

    // Test when cart value is much lower than the minimum order threshold
    @Test
    fun `should return large surcharge when cart value is much below minimum threshold`() {
        val cartValue = 100
        val minimumOrderThreshold = 500
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        
        assertEquals(400, result)
    }

    // Test with soo small cart value 
    @Test
    fun `should return surcharge for small cart value`() {
        val cartValue = 1
        val minimumOrderThreshold = 1000
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        
        assertEquals(999, result)
    }

    // Zero    
    @Test
    fun `should return 0 for all 0`() {
        val cartValue = 0
        val minimumOrderThreshold = 0
        
        val result = runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        
        assertEquals(0, result)
    }
    
    // Negative
    @Test
    fun `Negative calculatioon`() {
        val cartValue = -11
        val minimumOrderThreshold = -10
                
        assertFailsWith<Exception> {
            runBlocking{minSurchargeFee.minSurchargeFee(cartValue, minimumOrderThreshold)}
        }
    }
}
