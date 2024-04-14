package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem

interface NagerApi {

    suspend fun getHolidays(
        year: String,
        countryCode: String
    ): Resource<List<NagerResponseItem?>, NetworkError>

}