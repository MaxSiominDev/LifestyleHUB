package dev.maxsiomin.prodhse.feature.auth.data.remote

import dev.maxsiomin.prodhse.core.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.RandomUserResponse

interface RandomUserApi {

    suspend fun getRandomUser(): ResponseWithException<RandomUserResponse, Exception>

}