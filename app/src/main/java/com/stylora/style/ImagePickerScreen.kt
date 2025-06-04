package com.stylora.style

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagePickerScreen() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pickSingleImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } else {
                getContentLauncher.launch("image/*")
            }
        }) {
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
            Text("Selected URI: ${uri.lastPathSegment ?: uri.toString()}")
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
}