package dev.maxsiomin.prodhse.feature.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.maxsiomin.common.data.BidirectionalMapper
import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.data.mappers.FsqId
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlaceDetailsMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlaceMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlacePhotosMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlanMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherMapper
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class MappersModule {

    @Binds
    abstract fun bindPlanMapper(impl: PlanMapper): BidirectionalMapper<PlanEntity, Plan>

    @Binds
    abstract fun bindPlaceMapper(impl: PlaceMapper): ToDomainMapper<Result, Place?>

    @Binds
    abstract fun bindPlaceDetailsMapper(impl: PlaceDetailsMapper): ToDomainMapper<PlaceDetailsResponse, PlaceDetails?>

    @Binds
    abstract fun bindPlacePhotosMapper(impl: PlacePhotosMapper): ToDomainMapper<Pair<PlacePhotosResponseItem, FsqId>, Photo>

    @Binds
    abstract fun bindWeatherMapper(impl: WeatherMapper): ToDomainMapper<CurrentWeatherResponse, Weather>

}