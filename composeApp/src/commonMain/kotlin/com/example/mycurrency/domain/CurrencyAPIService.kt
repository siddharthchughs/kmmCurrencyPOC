package com.example.mycurrency.domain

import com.example.mycurrency.domain.model.RequestStates

interface CurrencyAPIService {
    suspend fun getLatestCurrencyRates(): RequestStates<Any>
}

