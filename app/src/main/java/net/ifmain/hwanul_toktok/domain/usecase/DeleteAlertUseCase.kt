package net.ifmain.hwanul_toktok.domain.usecase

import net.ifmain.hwanul_toktok.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class DeleteAlertUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    suspend operator fun invoke(alertId: Long): Result<Unit> {
        return repository.deleteAlert(alertId)
    }
}