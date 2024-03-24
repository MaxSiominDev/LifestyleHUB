package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import javax.inject.Inject

@HiltViewModel
class AddPlanViewModel @Inject constructor(
    private val repo: PlansRepository,
) : ViewModel() {



}