package com.stylora.style.data.remote

import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import kotlinx.coroutines.flow.Flow

interface FeedbackRemoteDataSource {
    suspend fun giveFeedback(giveFeedBackRequestModel: GiveFeedBackRequestModel): Flow<StyloraResponse<FeedbackResponseModel>>
    suspend fun getFeedbacks(): Flow<StyloraResponse<List<FeedbackResponseModel>>>

}