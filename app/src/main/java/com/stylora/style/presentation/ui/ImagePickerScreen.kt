package com.stylora.style.presentation.ui

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.stylora.style.R
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.presentation.viewmodel.FeedbackViewModel

@Composable
fun ImagePickerScreen(modifier: Modifier) {
    val viewModel: FeedbackViewModel = hiltViewModel()
    var singleImageUri by remember { mutableStateOf<Uri?>(null) }
    var showRemoveImageDialog by remember { mutableStateOf(false) }

    val pickSingleImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        singleImageUri = uri
    }

    val getContentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        singleImageUri = uri
    }

    val state by viewModel.state.collectAsState()
    val feedbackTypeItems = listOf("normal", "strict")
    val languageItems = listOf("English", "Spanish", "French", "Arabic")
    var feedbackTypeValue by remember { mutableStateOf("") }
    var languageValue by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(20.dp))

        DropdownTextField(
            feedbackTypeItems,
            label = "Feedback Type",
            selectedOption = feedbackTypeValue,
            onOptionSelected = {
                feedbackTypeValue = it
            },
            modifier = Modifier
                .width(220.dp),
            placeholder = "Feedback Type"
        )

        DropdownTextField(
            languageItems,
            label = "Language",
            selectedOption = languageValue,
            onOptionSelected = {
                languageValue = it
            },
            modifier = Modifier
                .width(220.dp),
            placeholder = "Language"
        )

        Button(
            modifier = Modifier, onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    pickSingleImageLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else {
                    getContentLauncher.launch("image/*")
                }
            }
        ) {
            Text("Pick Single Image")
        }
        Spacer(modifier = Modifier.height(16.dp))

        singleImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        showRemoveImageDialog = true
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier, onClick = {
                    val requestModel = GiveFeedBackRequestModel(
                        language = languageValue,
                        feedbackType = feedbackTypeValue,
                        imageUri = uri
                    )
                    viewModel.giveFeedback(requestModel)
                }, enabled = !state.isLoading
            ) {
                Text(text = if (!state.isLoading) "Get Feedback" else "Loading ...")
            }


            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.feedBackResponseModel?.let {
                    Text(
                        text = "Response Data:",
                        color = colorResource(R.color.black),
                        fontSize = 16.sp
                    )

                    Text(
                        text = it.message,
                        color = colorResource(R.color.black),
                        fontSize = 12.sp
                    )
                }

                state.error?.let {
                    Text(
                        text = it,
                        color = colorResource(R.color.red),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        if (showRemoveImageDialog) {
            AlertDialog(
                onDismissRequest = {
                    showRemoveImageDialog = false
                },
                title = { Text("Remove Image") },
                text = { Text("Do you want to remove the selected image?") },
                confirmButton = {
                    TextButton(onClick = {
                        singleImageUri = null
                        showRemoveImageDialog = false
                    }) {
                        Text("Remove")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showRemoveImageDialog = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
//}
}