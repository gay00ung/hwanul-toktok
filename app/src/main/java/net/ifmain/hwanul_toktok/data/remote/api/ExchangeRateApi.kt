package net.ifmain.hwanul_toktok.data.remote.api

import net.ifmain.hwanul_toktok.data.remote.dto.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("site/program/financial/exchangeJSON")
    suspend fun getExchangeRates(
        @Query("authkey") authKey: String,
        @Query("searchdate") searchDate: String? = null,
        @Query("data") dataType: String = "AP01"
    ): List<ExchangeRateDto>
}