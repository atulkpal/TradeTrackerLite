package com.atulpal.tradetrackerlite.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.atulpal.tradetrackerlite.ui.theme.DarkTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showExchangeRateDialog by remember { mutableStateOf(false) }
    
    var pendingCurrency by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Profile Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { showEditProfileDialog = true }
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(userProfile?.name ?: "Trader", style = MaterialTheme.typography.headlineSmall)
                userProfile?.email?.let {
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("Tap to edit profile", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }

            // Upgrade Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = DarkTertiary)
                            Spacer(Modifier.width(8.dp))
                            Text("Upgrade to Premium", style = MaterialTheme.typography.titleMedium)
                        }
                        Text(
                            "Advanced AI insights and unlimited trade attachments.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkTertiary)
                    ) {
                        Text("Go Pro")
                    }
                }
            }

            // Settings List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Preferences", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                SettingsItem(
                    icon = Icons.Default.DarkMode, 
                    title = "Dark Mode", 
                    hasSwitch = true,
                    switchChecked = isDarkMode,
                    onSwitchChange = { viewModel.toggleDarkMode(it) }
                )
                SettingsItem(
                    icon = Icons.Default.Payments, 
                    title = "Currency", 
                    trailingText = userProfile?.preferredCurrency ?: "INR",
                    onClick = { showCurrencyDialog = true }
                )
                
                Spacer(Modifier.height(16.dp))
                Text("Data & Storage", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                SettingsItem(Icons.Default.Download, "Export Data", trailingText = "CSV/JSON")
                SettingsItem(Icons.Default.Backup, "Backup & Restore")

                Spacer(Modifier.height(16.dp))
                SettingsItem(Icons.AutoMirrored.Filled.Logout, "Sign Out", iconColor = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(100.dp))
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        var name by remember { mutableStateOf(userProfile?.name ?: "") }
        var email by remember { mutableStateOf(userProfile?.email ?: "") }
        var phone by remember { mutableStateOf(userProfile?.phoneNumber ?: "") }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateProfile(name, email, phone, userProfile?.preferredCurrency ?: "INR")
                    showEditProfileDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Currency Selection Dialog
    if (showCurrencyDialog) {
        val currencies = listOf("INR", "USD", "EUR", "GBP", "JPY", "AED", "SGD")
        AlertDialog(
            onDismissRequest = { showCurrencyDialog = false },
            title = { Text("Select Currency") },
            text = {
                Column {
                    currencies.forEach { currency ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (currency != userProfile?.preferredCurrency) {
                                        pendingCurrency = currency
                                        showCurrencyDialog = false
                                        showExchangeRateDialog = true
                                    } else {
                                        showCurrencyDialog = false
                                    }
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = currency == userProfile?.preferredCurrency, onClick = null)
                            Spacer(Modifier.width(16.dp))
                            Text(currency)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    // Exchange Rate Dialog
    if (showExchangeRateDialog) {
        var rate by remember { mutableStateOf("1.0") }
        AlertDialog(
            onDismissRequest = { showExchangeRateDialog = false },
            title = { Text("Apply Exchange Rate?") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Do you want to convert existing trade values to $pendingCurrency?")
                    OutlinedTextField(
                        value = rate,
                        onValueChange = { rate = it },
                        label = { Text("Exchange Rate (1 ${userProfile?.preferredCurrency} = ? $pendingCurrency)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "If you choose 'Just Change Symbol', prices will remain the same.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val conversionFactor = rate.toDoubleOrNull()
                    viewModel.updateProfile(
                        userProfile?.name ?: "",
                        userProfile?.email,
                        userProfile?.phoneNumber,
                        pendingCurrency,
                        conversionFactor
                    )
                    showExchangeRateDialog = false
                }) {
                    Text("Convert Data")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.updateProfile(
                        userProfile?.name ?: "",
                        userProfile?.email,
                        userProfile?.phoneNumber,
                        pendingCurrency
                    )
                    showExchangeRateDialog = false
                }) {
                    Text("Just Change Symbol")
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    iconColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingText: String? = null,
    hasSwitch: Boolean = false,
    switchChecked: Boolean = false,
    onSwitchChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor)
                Spacer(Modifier.width(16.dp))
                Text(title, style = MaterialTheme.typography.bodyLarge)
            }
            if (hasSwitch) {
                Switch(checked = switchChecked, onCheckedChange = onSwitchChange)
            } else if (trailingText != null) {
                Text(trailingText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
