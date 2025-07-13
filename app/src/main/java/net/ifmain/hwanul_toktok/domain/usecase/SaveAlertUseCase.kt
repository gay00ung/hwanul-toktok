package net.ifmain.hwanul_toktok.domain.usecase

import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert
import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class SaveAlertUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    suspend operator fun invoke(alert: ExchangeRateAlert): Result<Long> {
        return repository.saveAlert(alert)
    }
}