package com.serhio.homeaccountingapp

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

// Updated SettingsMenu function in SettingsMenu.kt
@Composable
fun SettingsMenu(
    onDismiss: () -> Unit,
    onCurrencySelected: (String) -> Unit,
    onSaveSettings: () -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var selectedCurrency by remember { mutableStateOf(sharedPreferences.getString("currency", "UAH") ?: "UAH") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(enabled = true, onClick = {})
            .zIndex(1f),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Gray.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.8f))
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .widthIn(max = 300.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.select_currency),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CurrencyOption("UAH", selectedCurrency) { currency ->
                    selectedCurrency = currency
                    onCurrencySelected(currency)
                }
                CurrencyOption("USD", selectedCurrency) { currency ->
                    selectedCurrency = currency
                    onCurrencySelected(currency)
                }
                CurrencyOption("EUR", selectedCurrency) { currency ->
                    selectedCurrency = currency
                    onCurrencySelected(currency)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = R.string.close), color = Color.Red)
                }

                TextButton(onClick = {
                    saveSettings(sharedPreferences, selectedCurrency) // Save settings
                    onSaveSettings()
                    onDismiss()
                }) {
                    Text(stringResource(id = R.string.save), color = Color.Green)
                }
            }
        }
    }
}
@Composable
fun CurrencyOption(currency: String, selectedCurrency: String, onSelect: (String) -> Unit) {
    Button(
        onClick = { onSelect(currency) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currency == selectedCurrency) Color.Blue else Color.Blue.copy(alpha = 0.5f),
            contentColor = Color.White
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(text = currency)
    }
}

fun saveSettings(sharedPreferences: SharedPreferences, currency: String) {
    with(sharedPreferences.edit()) {
        putString("currency", currency)
        apply()
    }
}