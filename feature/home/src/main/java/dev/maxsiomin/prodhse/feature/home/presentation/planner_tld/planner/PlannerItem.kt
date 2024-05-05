package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan

internal data class PlannerItem(val place: PlaceDetails, val plan: Plan)
