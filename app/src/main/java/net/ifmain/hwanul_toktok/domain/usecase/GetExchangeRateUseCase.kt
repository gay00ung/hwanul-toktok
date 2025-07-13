package net.ifmain.hwanul_toktok.domain.usecase

import net.ifmain.hwanul_toktok.domain.model.ExchangeRate
import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    suspend operator fun invoke(currencyCode: String): Result<ExchangeRate> {
        return repository.getExchangeRate(currencyCode)
    }
}