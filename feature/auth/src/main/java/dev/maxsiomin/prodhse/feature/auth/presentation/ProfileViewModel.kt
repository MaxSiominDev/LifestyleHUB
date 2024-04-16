package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.Holiday
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivity
import dev.maxsiomin.prodhse.feature.auth.domain.repository.NagerRepository
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val randomActivityRepo: RandomActivityRepository,
    private val nagerRepo: NagerRepository,
) : ViewModel() {

    data class State(
        val authStatus: AuthStatus = AuthStatus.Loading,
        val randomActivity: RandomActivity? = null,
        val showRandomActivityDialog: Boolean = false,
        val nearestHoliday: Holiday? = null,
        val showNearestHolidayDialog: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set

    sealed class Event {
        data object LogoutClicked : Event()
        data object BoredClicked : Event()
        data object DismissRandomActivityDialog : Event()
        data object HolidayClicked : Event()
        data object DismissHolidayDialog : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LogoutClicked -> viewModelScope.launch {
                authManager.logout()
            }

            Event.BoredClicked -> state = state.copy(showRandomActivityDialog = true)
            Event.DismissRandomActivityDialog -> state = state.copy(showRandomActivityDialog = false)

            Event.HolidayClicked -> state = state.copy(showNearestHolidayDialog = true)
            Event.DismissHolidayDialog -> state = state.copy(showNearestHolidayDialog = false)
        }
    }

    init {
        val status = authManager.authStatus.value
        state = state.copy(authStatus = status)
        viewModelScope.launch {
            authManager.authStatus.collect {
                state = state.copy(authStatus = it)
            }
        }
        loadRandomActivity()
        loadHolidays()
    }

    private fun loadRandomActivity() {
        viewModelScope.launch {
            randomActivityRepo.getRandomActivity().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(randomActivity = resource.data)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun loadHolidays() {
        viewModelScope.launch {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            nagerRepo.getHolidays(year = "$year", countryCode = COUNTRY_CODE_FOR_NAGER)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            processHolidays(resource.data)
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun processHolidays(holidays: List<Holiday>) {
        val sorted = holidays.sortedBy { it.date }
        val nearest = sorted.firstOrNull {
            it.date > System.currentTimeMillis()
        }
        state = state.copy(nearestHoliday = nearest)
    }

    companion object {
        // Always ru since target audience are russians
        private const val COUNTRY_CODE_FOR_NAGER = "ru"
    }

}