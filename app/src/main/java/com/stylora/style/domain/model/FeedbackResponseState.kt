package com.stylora.style.domain.model

data class FeedbackResponseState(
    val isLoading: Boolean = false,
    val feedBackResponseModel: FeedbackResponseModel? = null,
    val error: String? = null
)