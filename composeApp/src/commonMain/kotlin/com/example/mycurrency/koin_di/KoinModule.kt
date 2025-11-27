package com.example.mycurrency.koin_di

import com.example.mycurrency.data.api_service.CurrencyApiServiceImpl
import com.example.mycurrency.data.api_service.localstorage.PreferencesImpl
import com.example.mycurrency.domain.CurrencyAPIService
import com.example.mycurrency.domain.PreferenceRepository
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { Settings() }
    single<PreferenceRepository> { PreferencesImpl(settings = get()) }
    single<CurrencyAPIService> { CurrencyApiServiceImpl(preferenceRepository = get()) }
}

fun initialize() {
    startKoin {
        modules(appModule)
    }
}
