package com.example.mycurrency.data.api_service

import com.example.mycurrency.domain.CurrencyAPIService
import com.example.mycurrency.domain.PreferenceRepository
import com.example.mycurrency.domain.model.CurrencyCode
import com.example.mycurrency.domain.model.CurrencyNetworkResponse
import com.example.mycurrency.domain.model.RequestStates
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CurrencyApiServiceImpl(
    private val preferenceRepository: PreferenceRepository
) : CurrencyAPIService {

    companion object {
        const val BASE_URL = "https://api.currencyapi.com/v3/"
        const val LATEST_PATH = "latest"
        const val API_KEY = "cur_live_5Ks3v5RaTES1BYDT4Sxdw8B2NajRQu5vzyIZHa4E"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }

        install(DefaultRequest) {
            headers {
                url(BASE_URL)
                url.parameters.append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestCurrencyRates(): RequestStates<Any> {
        return try {
            val response = httpClient.get(LATEST_PATH)
            if (response.status.value == 200) {
                println("API_RESPONSE: ${response.body<String>()}")
                val responseBody = Json.decodeFromString<CurrencyNetworkResponse>(response.body())
                val availableCurrency = responseBody.data.keys
                    .filter {
                        CurrencyCode.entries
                            .map { code -> code.country }
                            .toSet()
                            .contains(it)
                    }
                // To persist the last updated time stamp.
                val lastUpdatedTimeStamp = responseBody.meta.lastUpdatedAt
                preferenceRepository.savedLastUpdatedTimeStamp(lastUpdatedTimeStamp)
                RequestStates.Success(data = availableCurrency)
            } else {
                RequestStates.Error(message = "Http Error Code : ${response.status.value}")
            }
        } catch (e: Exception) {
            RequestStates.Error(message = e.message.toString())
        }
    }
}