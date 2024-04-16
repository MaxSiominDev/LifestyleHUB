package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem

interface NagerApi {

    suspend fun getHolidays(
        year: String,
        countryCode: String
    ): Resource<List<NagerResponseItem?>, NetworkError>

}