package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel

class PlaceDetailsDtoToUoModelMapper : (PlaceDetailsResponse) -> PlaceDetailsModel? {

    override fun invoke(detailsDto: PlaceDetailsResponse): PlaceDetailsModel? {
        val timeUpdated = System.currentTimeMillis()
        val category = detailsDto.categories?.mapNotNull { it?.name }
        if (category.isNullOrEmpty()) return null
        val address = detailsDto.location?.formattedAddress ?: return null
        val name = detailsDto.name ?: return null
        val id = detailsDto.fsqId ?: return null
        val rating = detailsDto.rating
        val website = detailsDto.website
        val isVerified = detailsDto.verified
        val workingHours = detailsDto.hours?.display?.split(";")?.map { it.trim() }
        val isOpenNow = detailsDto.hours?.openNow
        val photos = detailsDto.photos?.mapNotNull {
            it?.let {
                PhotoModel(id = id, url = "${it.prefix}original${it.suffix}")
            }
        } ?: emptyList()

        return PlaceDetailsModel(
            timeUpdated = timeUpdated,
            categories = category.joinToString(separator = ","),
            address = address,
            name = name,
            rating = rating,
            website = website,
            workingHours = workingHours,
            isVerified = isVerified ?: false,
            isOpenNow = isOpenNow ?: false,
            photos = photos,
            fsqId = id,
        )
    }

}