package net.ifmain.hwanul_toktok.presentation.state

import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert

data class AlertUiState(
    val isLoading: Boolean = false,
    val alerts: List<ExchangeRateAlert> = emptyList(),
    val error: String? = null
)