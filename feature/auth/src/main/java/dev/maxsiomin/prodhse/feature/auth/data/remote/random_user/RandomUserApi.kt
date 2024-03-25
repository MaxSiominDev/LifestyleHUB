package dev.maxsiomin.prodhse.feature.auth.data.remote.random_user

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.RandomUserResponse

interface RandomUserApi {

    suspend fun getRandomUser(): ResponseWithException<RandomUserResponse, Exception>

}