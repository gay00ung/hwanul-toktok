package net.ifmain.hwanul_toktok.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert
import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetAlertsUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    operator fun invoke(): Flow<List<ExchangeRateAlert>> {
        return repository.getAlertsFlow()
    }
}