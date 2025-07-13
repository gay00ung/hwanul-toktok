package net.ifmain.hwanul_toktok.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.ifmain.hwanul_toktok.domain.usecase.GetExchangeRatesUseCase
import net.ifmain.hwanul_toktok.presentation.state.ExchangeRateUiState
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExchangeRateUiState())
    val uiState: StateFlow<ExchangeRateUiState> = _uiState.asStateFlow()
    
    init {
        loadExchangeRates()
    }
    
    fun loadExchangeRates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            getExchangeRatesUseCase()
                .onSuccess { rates ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            exchangeRates = rates,
                            lastUpdateTime = System.currentTimeMillis()
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    }
                }
        }
    }
    
    fun toggleCurrencySelection(currencyCode: String) {
        _uiState.update { state ->
            val newSelection = if (currencyCode in state.selectedCurrencies) {
                state.selectedCurrencies - currencyCode
            } else {
                state.selectedCurrencies + currencyCode
            }
            state.copy(selectedCurrencies = newSelection)
        }
    }
    
    fun getFilteredExchangeRates() = _uiState.value.exchangeRates.filter { rate ->
        rate.currencyCode in _uiState.value.selectedCurrencies
    }
}