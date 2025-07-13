package net.ifmain.hwanul_toktok.domain.model

data class ExchangeRateAlert(
    val id: Long = 0,
    val currencyCode: String,
    val targetRate: Double,
    val alertType: AlertType,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class AlertType {
    ABOVE,
    BELOW
}