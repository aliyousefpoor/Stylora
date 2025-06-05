package com.stylora.style.domain.usecase

import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import com.stylora.style.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiveFeedbackUseCase @Inject constructor(private val repository: FeedbackRepository) {
    suspend fun invoke(requestModel: GiveFeedBackRequestModel): Flow<StyloraResponse<FeedbackResponseModel>> {
        return repository.giveFeedback(requestModel)
    }
}