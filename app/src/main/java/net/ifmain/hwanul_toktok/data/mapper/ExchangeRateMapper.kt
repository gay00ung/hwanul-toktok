package net.ifmain.hwanul_toktok.data.mapper

import net.ifmain.hwanul_toktok.data.remote.dto.ExchangeRateDto
import net.ifmain.hwanul_toktok.domain.model.ExchangeRate

fun ExchangeRateDto.toDomainModel(): ExchangeRate {
    return ExchangeRate(
        currencyCode = extractCurrencyCode(currencyUnit),
        currencyName = currencyName,
        currencyUnit = currencyUnit,
        buyingRate = ttb.replace(",", "").toDoubleOrNull() ?: 0.0,
        sellingRate = tts.replace(",", "").toDoubleOrNull() ?: 0.0,
        baseRate = dealBaseRate.replace(",", "").toDoubleOrNull() ?: 0.0,
        bookPrice = bookPrice.replace(",", "").toDoubleOrNull() ?: 0.0
    )
}

private fun extractCurrencyCode(currencyUnit: String): String {
    return currencyUnit.substringBefore("(").trim()
}