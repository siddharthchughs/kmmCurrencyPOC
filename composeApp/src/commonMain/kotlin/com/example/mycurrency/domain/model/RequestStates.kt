package com.example.mycurrency.domain.model

sealed class RequestStates<out T> {
    data object Idle : RequestStates<Nothing>()
    data object Loading : RequestStates<Nothing>()
    data class Success<out T>(val data: T) : RequestStates<T>()
    data class Error(val message: String) : RequestStates<String>()

    fun isLoading(): Boolean = this is Loading
    fun isError(): Boolean = this is Error
    fun isSuccess(): Boolean = this is Success

    fun getSuccessData() = (this as Success).data
    fun getErrorMessage(): String = (this as Error).message

}