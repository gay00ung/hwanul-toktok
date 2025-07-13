package net.ifmain.hwanul_toktok.domain.model

data class ExchangeRate(
    val currencyCode: String,
    val currencyName: String,
    val currencyUnit: String,
    val buyingRate: Double,
    val sellingRate: Double,
    val baseRate: Double,
    val bookPrice: Double,
    val timestamp: Long = System.currentTimeMillis()
)