package net.ifmain.hwanul_toktok.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.ifmain.hwanul_toktok.domain.model.AlertType
import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert
import net.ifmain.hwanul_toktok.domain.usecase.DeleteAlertUseCase
import net.ifmain.hwanul_toktok.domain.usecase.GetAlertsUseCase
import net.ifmain.hwanul_toktok.domain.usecase.SaveAlertUseCase
import net.ifmain.hwanul_toktok.presentation.state.AlertUiState
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val saveAlertUseCase: SaveAlertUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
    private val getAlertsUseCase: GetAlertsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AlertUiState())
    val uiState: StateFlow<AlertUiState> = _uiState.asStateFlow()
    
    init {
        observeAlerts()
    }
    
    private fun observeAlerts() {
        viewModelScope.launch {
            getAlertsUseCase().collect { alerts ->
                _uiState.update { it.copy(alerts = alerts) }
            }
        }
    }
    
    fun saveAlert(
        currencyCode: String,
        targetRate: Double,
        alertType: AlertType
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val alert = ExchangeRateAlert(
                currencyCode = currencyCode,
                targetRate = targetRate,
                alertType = alertType
            )
            
            saveAlertUseCase(alert)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "알림 저장에 실패했습니다."
                        )
                    }
                }
        }
    }
    
    fun deleteAlert(alertId: Long) {
        viewModelScope.launch {
            deleteAlertUseCase(alertId)
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            error = exception.message ?: "알림 삭제에 실패했습니다."
                        )
                    }
                }
        }
    }
}