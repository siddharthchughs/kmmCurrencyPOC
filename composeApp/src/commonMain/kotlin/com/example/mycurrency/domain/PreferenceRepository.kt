package com.example.mycurrency.domain

interface PreferenceRepository {
    suspend fun savedLastUpdatedTimeStamp(lastUpdated: String)
    suspend fun isTimeStampNew(currentTimeStamp: Long): Boolean
}