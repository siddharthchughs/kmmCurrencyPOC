package com.example.mycurrency.data.api_service.localstorage

import com.example.mycurrency.domain.PreferenceRepository
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

class PreferencesImpl(private val settings: Settings) : PreferenceRepository {

    companion object {
        const val LAST_UPDATED_TIMESTAMP = "LastUpdated"
    }

    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    @OptIn(ExperimentalTime::class)
    override suspend fun savedLastUpdatedTimeStamp(lastUpdated: String) {
        flowSettings.putLong(
            key = LAST_UPDATED_TIMESTAMP,
            value = Instant.parse(lastUpdated).toEpochMilliseconds()
        )
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun isTimeStampNew(currentTimeStamp: Long): Boolean {
        val savedTimestamp = flowSettings.getLong(
            key = LAST_UPDATED_TIMESTAMP,
            defaultValue = 0L,
        )
        return if (savedTimestamp != OL) {
            val currentTimeStamp = Instant.fromEpochMilliseconds(currentTimeStamp)
            val savedTimeStamp = Instant.fromEpochMilliseconds(savedTimestamp)

            val currentDateTime = currentTimeStamp.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedTimeStamp.toLocalDateTime(TimeZone.currentSystemDefault())

            val dayDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
            dayDifference < 1
        } else false
    }
}