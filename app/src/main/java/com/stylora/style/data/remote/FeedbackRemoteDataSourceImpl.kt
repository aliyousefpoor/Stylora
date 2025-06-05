package com.stylora.style.data.remote

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import com.stylora.style.data.service.FeedbackApi
import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FeedbackRemoteDataSourceImpl @Inject constructor(
    private val context: Context,
    private val service: FeedbackApi
) :
    FeedbackRemoteDataSource {
    override suspend fun giveFeedback(giveFeedBackRequestModel: GiveFeedBackRequestModel): Flow<FeedbackResponseModel> =
        channelFlow {
            val imagePart = prepareImageFilePart(giveFeedBackRequestModel.imageUri, context)
            val deviceID =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            trySend(
                service.giveFeedback(
                    image_file = imagePart,
                    feedbackType = giveFeedBackRequestModel.feedbackType,
                    deviceId = deviceID,
                    language = giveFeedBackRequestModel.language
                )
            )
        }

    private fun prepareImageFilePart(uri: Uri, context: Context): MultipartBody.Part {
        val file = File(getRealPathFromUri(context, uri))

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val fileName = cursor.getString(index)
            cursor.close()

            val inputStream = contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } else {
            uri.path ?: ""
        }
    }


}