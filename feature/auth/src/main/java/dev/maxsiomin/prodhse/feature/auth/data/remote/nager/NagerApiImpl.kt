package dev.maxsiomin.prodhse.feature.auth.data.remote.nager

import dev.maxsiomin.prodhse.core.data.safeGet
import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import javax.inject.Inject

class NagerApiImpl @Inject constructor(private val client: HttpClient) : NagerApi {

    override suspend fun getHolidays(year: String, countryCode: String): Resource<List<NagerResponseItem?>, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.getHolidaysUrl(year = year, countryCode = countryCode))
        }
    }

}