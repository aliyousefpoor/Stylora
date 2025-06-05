package com.stylora.style.data.service

import com.stylora.style.domain.model.FeedbackResponseModel
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FeedbackApi {
    @Multipart
    @POST("feedback/give-feedback")
    suspend fun giveFeedback(
        @Part image_file: MultipartBody.Part,
        @Part("feedback_type") feedbackType: String,
        @Part("device_id") deviceId: String,
        @Part("language") language: String
    ): FeedbackResponseModel
}