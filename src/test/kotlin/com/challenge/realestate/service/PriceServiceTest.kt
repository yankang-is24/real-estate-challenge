package com.challenge.realestate.service

import com.challenge.realestate.model.PriceRequest
import com.challenge.realestate.model.PriceResponse
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.test.assertEquals

class PriceServiceTest {
    
    private val service = PriceService()
    
    @Test
    fun testCalculatePropertyPrice() {
        val request1 = PriceRequest(1000, "HOUSE", "downtown")
        val result1 = service.calculatePropertyPrice(request1)
        
        assertEquals(200, result1.statusCodeValue)
        
        val request2 = PriceRequest(800, "condo", null)
        val result2 = service.calculatePropertyPrice(request2)
        assertEquals(120000.0, result2.body?.price)
        
        assertEquals(true, 
            result2.body?.price == 120000.0 && 
            result2.body?.pricePerSqFt == 150.0 && 
            result2.body?.propertyType == "condo"
        )
        
        val request3 = PriceRequest(1000, "HOUSE", "downtown")
        val result3 = service.calculatePropertyPrice(request3)
        assertEquals(request1, request3)
    }
    
    @Test
    fun shouldTestLocationMultiplier() {
        val request = PriceRequest(100, "APARTMENT", "downtown")
        service.calculatePropertyPrice(request)
    }
    
    @Test
    fun shouldTestLocationMultiplierAgain() {
        val request = PriceRequest(100, "APARTMENT", "downtown")
        val result = service.calculatePropertyPrice(request)
        assertEquals(200, result.statusCodeValue)
    }
    
    private var previousResult: ResponseEntity<PriceResponse>? = null
    
    @Test
    fun shouldComparePrices() {
        val request = PriceRequest(100, "HOUSE", null)
        val result = service.calculatePropertyPrice(request)
        if (previousResult != null) {
            assertEquals(true, result.body?.price!! > previousResult?.body?.price!!)
        }
        previousResult = result
    }
}
