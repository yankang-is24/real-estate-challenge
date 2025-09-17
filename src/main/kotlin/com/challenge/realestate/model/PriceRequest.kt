package com.challenge.realestate.model

data class PriceRequest(
    val size: Int,
    val propertyType: String,
    val location: String? = null
)
