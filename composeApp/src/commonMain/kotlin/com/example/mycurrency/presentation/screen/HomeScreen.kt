package com.example.mycurrency.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import com.example.mycurrency.data.api_service.CurrencyApiServiceImpl

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        LaunchedEffect(Unit) {
            CurrencyApiServiceImpl().getLatestCurrencyRates()
        }
    }
}