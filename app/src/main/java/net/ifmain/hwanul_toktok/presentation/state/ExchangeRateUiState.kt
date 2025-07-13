package net.ifmain.hwanul_toktok.presentation.state

import net.ifmain.hwanul_toktok.domain.model.ExchangeRate

data class ExchangeRateUiState(
    val isLoading: Boolean = false,
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val selectedCurrencies: Set<String> = setOf("USD", "EUR", "JPY(100)", "CNH"),
    val error: String? = null,
    val lastUpdateTime: Long? = null
)