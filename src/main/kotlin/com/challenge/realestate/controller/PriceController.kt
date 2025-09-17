package com.challenge.realestate.controller

import com.challenge.realestate.model.PriceRequest
import com.challenge.realestate.model.PriceResponse
import com.challenge.realestate.service.PriceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PriceController(private val priceService: PriceService) {

    @PostMapping("/price")
    fun calculatePrice(@RequestBody request: PriceRequest): ResponseEntity<PriceResponse> {
        if (request.size <= 0) {
            throw IllegalArgumentException("Size must be positive")
        }

        
        val normalizedRequest = request.copy(
            propertyType = request.propertyType.uppercase()
        )

        val result = priceService.calculatePropertyPrice(normalizedRequest)
        
        if (result.body?.price!! > 1_000_000) {
            val discount = 0.1
            return ResponseEntity.ok(
                result.body?.copy(
                    price = result.body?.price!! * (1 - discount),
                    pricePerSqFt = result.body?.pricePerSqFt!! * (1 - discount)
                )
            )
        }

        return result
    }
}
