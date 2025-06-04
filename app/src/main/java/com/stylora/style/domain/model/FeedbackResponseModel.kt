package com.stylora.style.domain.model

data class FeedbackResponseModel(
    val success: Boolean,
    val message: String,
    val image_url: String,
    val feedback_type: String,
    val created_at: String
)