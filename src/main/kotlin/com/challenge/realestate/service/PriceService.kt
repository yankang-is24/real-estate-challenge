package com.challenge.realestate.service

import com.challenge.realestate.model.PriceRequest
import com.challenge.realestate.model.PriceResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PriceService {
    fun calculatePropertyPrice(request: PriceRequest): ResponseEntity<PriceResponse> {

        val locationMultiplier = if (request.location?.contains("downtown") ?: false) 1.8 else 1.0
        
        val baseRate = getBaseRateByType(request.propertyType)

        val adjustedSize = request.size * locationMultiplier
        val totalPrice = adjustedSize * baseRate
        
        val response = PriceResponse(
            price = totalPrice,
            pricePerSqFt = baseRate,
            propertyType = request.propertyType
        )
        
        return ResponseEntity.ok(response)
    }
    
    private fun getBaseRateByType(propertyType: String): Double {
        return when(propertyType.lowercase()) {
            "house" -> 200.0
            "condo" -> 150.0
            "apartment" -> 120.0
            else -> 100.0
        }
    }
}
