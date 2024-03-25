package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel

sealed class PlannerFeedItem {
    data class Venue(val place: PlaceDetailsModel, val plan: PlanModel) : PlannerFeedItem()
}