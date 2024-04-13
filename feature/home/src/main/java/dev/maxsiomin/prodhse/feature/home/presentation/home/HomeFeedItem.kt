package dev.maxsiomin.prodhse.feature.home.presentation.home

import dev.maxsiomin.prodhse.feature.home.domain.PlaceModel

sealed class HomeFeedItem {
    data object Weather : HomeFeedItem()
    data class Venue(val placeModel: PlaceModel) : HomeFeedItem()
}