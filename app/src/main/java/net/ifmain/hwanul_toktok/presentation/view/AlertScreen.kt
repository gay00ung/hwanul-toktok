package net.ifmain.hwanul_toktok.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import net.ifmain.hwanul_toktok.domain.model.AlertType
import net.ifmain.hwanul_toktok.domain.model.ExchangeRateAlert
import net.ifmain.hwanul_toktok.presentation.viewmodel.AlertViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScreen(
    onNavigateBack: () -> Unit,
    viewModel: AlertViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("환율 알림 설정") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "알림 추가")
            }
        }
    ) { paddingValues ->
        if (uiState.alerts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "설정된 알림이 없습니다.\n+ 버튼을 눌러 알림을 추가하세요.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.alerts) { alert ->
                    AlertCard(
                        alert = alert,
                        onDelete = { viewModel.deleteAlert(alert.id) }
                    )
                }
            }
        }
    }
    
    if (showAddDialog) {
        AddAlertDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { currency, rate, type ->
                viewModel.saveAlert(currency, rate, type)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AlertCard(
    alert: ExchangeRateAlert,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = alert.currencyCode,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${formatCurrency(alert.targetRate)}원 ${
                        when (alert.alertType) {
                            AlertType.ABOVE -> "이상"
                            AlertType.BELOW -> "이하"
                        }
                    }",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "삭제",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AddAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Double, AlertType) -> Unit
) {
    var selectedCurrency by remember { mutableStateOf("USD") }
    var targetRate by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(AlertType.ABOVE) }
    
    val currencies = listOf("USD", "EUR", "JPY(100)", "CNH")
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("알림 추가") },
        text = {
            Column {
                Text("통화 선택", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                currencies.forEach { currency ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCurrency == currency,
                            onClick = { selectedCurrency = currency }
                        )
                        Text(currency)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = targetRate,
                    onValueChange = { targetRate = it },
                    label = { Text("목표 환율") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("알림 조건", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedType == AlertType.ABOVE,
                        onClick = { selectedType = AlertType.ABOVE }
                    )
                    Text("이상")
                    
                    Spacer(modifier = Modifier.width(32.dp))
                    
                    RadioButton(
                        selected = selectedType == AlertType.BELOW,
                        onClick = { selectedType = AlertType.BELOW }
                    )
                    Text("이하")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    targetRate.toDoubleOrNull()?.let { rate ->
                        onConfirm(selectedCurrency, rate, selectedType)
                    }
                }
            ) {
                Text("추가")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

private fun formatCurrency(value: Double): String {
    val formatter = DecimalFormat("#,##0.00")
    return formatter.format(value)
}