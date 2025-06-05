package com.stylora.style.domain.model

data class FeedbackResponseState(
    val isLoading: Boolean = false,
    val feedBackResponseModel: List<FeedbackResponseModel>? = null,
    val error: String? = null
)