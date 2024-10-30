package com.ovedev.coordinadoraconnect.data

sealed class Response<out T> {
    data class Loading(val isLoading: Boolean) : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val errorMessage: String) : Response<Nothing>()
}