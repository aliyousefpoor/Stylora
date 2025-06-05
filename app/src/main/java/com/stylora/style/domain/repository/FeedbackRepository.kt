package com.stylora.style.domain.repository

import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {
    suspend fun giveFeedback(giveFeedBackRequestModel: GiveFeedBackRequestModel): Flow<StyloraResponse<FeedbackResponseModel>>
    suspend fun getFeedbacks(): Flow<StyloraResponse<List<FeedbackResponseModel>>>
}