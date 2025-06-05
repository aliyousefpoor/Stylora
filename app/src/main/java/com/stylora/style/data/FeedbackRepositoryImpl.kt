package com.stylora.style.data

import com.stylora.style.data.remote.FeedbackRemoteDataSource
import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import com.stylora.style.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(private val dataSource: FeedbackRemoteDataSource) :
    FeedbackRepository {
    override suspend fun giveFeedback(giveFeedBackRequestModel: GiveFeedBackRequestModel): Flow<StyloraResponse<FeedbackResponseModel>> {
        return dataSource.giveFeedback(giveFeedBackRequestModel)
    }
}