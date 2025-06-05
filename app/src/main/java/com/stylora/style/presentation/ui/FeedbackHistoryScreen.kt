package com.stylora.style.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stylora.style.presentation.viewmodel.FeedbackViewModel

@Composable
fun FeedbackHistoryScreen() {
    val viewModel: FeedbackViewModel = hiltViewModel()
    val state by viewModel.historyState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getFeedbackHistory()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "History List:", fontSize = 16.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.padding(12.dp))
        if (state.isLoading) {
            Text(
                text = "Loading...", fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        state.feedBackResponseModel?.let { models ->
            LazyColumn(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (models.isNotEmpty()) {
                    itemsIndexed(models) { index, model ->
                        Text(text = "$index. ${model.feedback_type}")
                        Spacer(modifier = Modifier.padding(12.dp))
                    }
                } else
                    item {
                        Text(text = "No have available history")
                    }
            }
        }
    }
}