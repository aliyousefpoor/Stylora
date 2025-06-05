package com.stylora.style.domain.model

sealed class StyloraResponse<out T> {
    data object Loading : StyloraResponse<Nothing>()
    data class Success<out T>(val responseModel: T) : StyloraResponse<T>()
    data class Error(val message: String) : StyloraResponse<Nothing>()
}