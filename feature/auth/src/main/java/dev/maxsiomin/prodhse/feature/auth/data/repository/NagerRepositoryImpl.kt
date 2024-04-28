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
    ): Flow<Resource<List<Holiday>, NetworkError>> {
        return flow {
            val apiResponse = api.getHolidays(year = year, countryCode = countryCode)
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> apiResponse.data.let(mapper)?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error(NetworkError.EmptyResponse))
            }
        }
    }

}