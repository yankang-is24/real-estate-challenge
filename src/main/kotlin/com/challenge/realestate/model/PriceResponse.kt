package com.challenge.realestate.model

data class PriceResponse(
    val price: Double,
    val pricePerSqFt: Double,
    val propertyType: String
)
