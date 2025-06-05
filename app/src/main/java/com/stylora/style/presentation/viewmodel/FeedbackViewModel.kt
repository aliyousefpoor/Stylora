package com.stylora.style.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylora.style.domain.model.FeedbackResponseState
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import com.stylora.style.domain.usecase.GiveFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(private val feedbackUseCase: GiveFeedbackUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(FeedbackResponseState())
    val state: StateFlow<FeedbackResponseState> = _state


    fun giveFeedback(feedBackRequestModel: GiveFeedBackRequestModel) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            feedbackUseCase.invoke(feedBackRequestModel).collectLatest { response ->
                when (response) {
                    is StyloraResponse.Loading -> _state.update {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is StyloraResponse.Success ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                feedBackResponseModel = response.responseModel
                            )
                        }


                    is StyloraResponse.Error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = response.message
                            )
                        }

                }

            }
        }
    }
}