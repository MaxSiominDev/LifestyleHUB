package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.auth.data.dto.bored.BoredApiResponse
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem

interface NagerApi {

    suspend fun getHolidays(
        year: String,
        countryCode: String
    ): ResponseWithException<List<NagerResponseItem?>, Exception>

}