package com.example.cinemalab.domain.usecase.profile

import android.content.Context
import com.example.cinemalab.domain.repository.ProfileRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PostUserAvatarUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(image: File) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        //TODO: конвертить через битмапу в конкретный тип/обработать разные форматы картинок
        val body = image.asRequestBody("image/png".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", "image.png", body)

        return repository.uploadPhoto(bearerToken, filePart)
    }
}