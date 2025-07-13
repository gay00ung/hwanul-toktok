package net.ifmain.hwanul_toktok.domain.repository

import kotlinx.coroutines.flow.Flow
import net.ifmain.hwanul_toktok.domain.model.ExchangeRate
import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert

interface ExchangeRateRepository {
    suspend fun getExchangeRates(date: String? = null): Result<List<ExchangeRate>>
    
    suspend fun getExchangeRate(currencyCode: String): Result<ExchangeRate>
    
    fun getExchangeRatesFlow(): Flow<List<ExchangeRate>>
    
    suspend fun saveAlert(alert: ExchangeRateAlert): Result<Long>
    
    suspend fun deleteAlert(alertId: Long): Result<Unit>
    
    suspend fun getAlerts(): Result<List<ExchangeRateAlert>>
    
    fun getAlertsFlow(): Flow<List<ExchangeRateAlert>>
    
    suspend fun updateAlert(alert: ExchangeRateAlert): Result<Unit>
}