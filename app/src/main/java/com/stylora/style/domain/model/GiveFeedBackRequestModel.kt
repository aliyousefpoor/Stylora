package com.stylora.style.domain.model

import android.net.Uri

data class GiveFeedBackRequestModel(
    val language: String,
    val feedbackType: String,
    val imageUri: Uri
)