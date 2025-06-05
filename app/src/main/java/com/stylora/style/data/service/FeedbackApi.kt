package com.stylora.style.data.service

import com.stylora.style.domain.model.FeedbackResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FeedbackApi {
    @Multipart
    @POST("/api/v1/feedback/give-feedback")
    suspend fun giveFeedback(
        @Part image_file: MultipartBody.Part,
        @Part("feedback_type") feedbackType: RequestBody,
        @Part("device_id") deviceId: RequestBody,
        @Part("language") language: RequestBody
    ): Response<FeedbackResponseModel>

    @GET("/api/v1/feedback/user-feedbacks/{device_id}")
    suspend fun getUserFeedbacks(
        @Path("device_id") deviceId: String
    ): Response<List<FeedbackResponseModel>>
}