package net.ifmain.hwanul_toktok.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.ifmain.hwanul_toktok.BuildConfig
import net.ifmain.hwanul_toktok.data.mapper.toDomainModel
import net.ifmain.hwanul_toktok.data.remote.api.ExchangeRateApi
import net.ifmain.hwanul_toktok.domain.model.ExchangeRate
import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert
import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val api: ExchangeRateApi
) : ExchangeRateRepository {
    
    override suspend fun getExchangeRates(date: String?): Result<List<ExchangeRate>> {
        return try {
            val response = api.getExchangeRates(
                authKey = BuildConfig.KOREAEXIM_API_KEY,
                searchDate = date
            )
            val exchangeRates = response.map { it.toDomainModel() }
            Result.success(exchangeRates)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getExchangeRate(currencyCode: String): Result<ExchangeRate> {
        return try {
            val response = api.getExchangeRates(
                authKey = BuildConfig.KOREAEXIM_API_KEY
            )
            val exchangeRate = response
                .map { it.toDomainModel() }
                .find { it.currencyCode == currencyCode }
            
            exchangeRate?.let {
                Result.success(it)
            } ?: Result.failure(NoSuchElementException("Currency not found: $currencyCode"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getExchangeRatesFlow(): Flow<List<ExchangeRate>> = flow {
        try {
            val response = api.getExchangeRates(
                authKey = BuildConfig.KOREAEXIM_API_KEY
            )
            emit(response.map { it.toDomainModel() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    override suspend fun saveAlert(alert: ExchangeRateAlert): Result<Long> {
        // TODO: Implement with Room database
        return Result.success(System.currentTimeMillis())
    }
    
    override suspend fun deleteAlert(alertId: Long): Result<Unit> {
        // TODO: Implement with Room database
        return Result.success(Unit)
    }
    
    override suspend fun getAlerts(): Result<List<ExchangeRateAlert>> {
        // TODO: Implement with Room database
        return Result.success(emptyList())
    }
    
    override fun getAlertsFlow(): Flow<List<ExchangeRateAlert>> = flow {
        // TODO: Implement with Room database
        emit(emptyList())
    }
    
    override suspend fun updateAlert(alert: ExchangeRateAlert): Result<Unit> {
        // TODO: Implement with Room database
        return Result.success(Unit)
    }
}