package net.ifmain.hwanul_toktok.domain.usecase

import net.ifmain.hwanul_toktok.domain.model.ExchangeRate
import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    suspend operator fun invoke(date: String? = null): Result<List<ExchangeRate>> {
        return repository.getExchangeRates(date)
    }
}