package com.stylora.style.data.remote

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import com.stylora.style.data.service.FeedbackApi
import com.stylora.style.domain.model.FeedbackResponseModel
import com.stylora.style.domain.model.GiveFeedBackRequestModel
import com.stylora.style.domain.model.StyloraResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FeedbackRemoteDataSourceImpl @Inject constructor(
    private val context: Context,
    private val service: FeedbackApi
) :
    FeedbackRemoteDataSource {
    override suspend fun giveFeedback(giveFeedBackRequestModel: GiveFeedBackRequestModel): Flow<StyloraResponse<FeedbackResponseModel>> =
        channelFlow {
            withContext(Dispatchers.IO) {
                val imagePart = prepareImageFilePart(giveFeedBackRequestModel.imageUri, context)
                val deviceID =
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                val response = service.giveFeedback(
                    image_file = imagePart,
                    feedbackType = giveFeedBackRequestModel.feedbackType.toRequestBody("text/plain".toMediaTypeOrNull()),
                    deviceId = deviceID.toRequestBody("text/plain".toMediaTypeOrNull()),
                    language = giveFeedBackRequestModel.language.toRequestBody("text/plain".toMediaTypeOrNull())
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(StyloraResponse.Success(it))
                    }
                } else {
                    trySend(StyloraResponse.Error(response.errorBody()?.string() ?: ""))
                }
            }
        }

    override suspend fun getFeedbacks(): Flow<StyloraResponse<List<FeedbackResponseModel>>> =
        channelFlow {
            withContext(Dispatchers.IO) {
                val deviceID =
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                val response = service.getUserFeedbacks(deviceID)

                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(StyloraResponse.Success(it))
                    }
                } else {
                    trySend(StyloraResponse.Error(response.errorBody()?.string() ?: ""))
                }
            }
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