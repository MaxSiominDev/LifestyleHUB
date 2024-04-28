package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.HolidayDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.nager.NagerApi
import dev.maxsiomin.prodhse.feature.auth.domain.model.Holiday
import dev.maxsiomin.prodhse.feature.auth.domain.repository.NagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NagerRepositoryImpl @Inject constructor(
    private val api: NagerApi,
    private val mapper: HolidayDtoToUiModelMapper,
) : NagerRepository {

    override suspend fun getHolidays(
        year: String,
        countryCode: String
    ): Resource<List<Holiday>, NetworkError> {
        val apiResponse = api.getHolidays(year = year, countryCode = countryCode)
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> apiResponse.data.let(mapper)?.let {
                Resource.Success(it)
            } ?: Resource.Error(NetworkError.EmptyResponse)
        }
    }

}