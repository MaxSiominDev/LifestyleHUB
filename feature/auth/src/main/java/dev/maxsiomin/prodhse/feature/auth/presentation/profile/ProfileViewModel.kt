package dev.maxsiomin.prodhse.feature.auth.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.feature.auth.domain.model.Holiday
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomActivity
import dev.maxsiomin.prodhse.feature.auth.domain.repository.NagerRepository
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val randomActivityRepo: RandomActivityRepository,
    private val nagerRepo: NagerRepository,
) : StatefulViewModel<ProfileViewModel.State, Nothing, ProfileViewModel.Event>() {

    data class State(
        val authStatus: AuthStatus = AuthStatus.Loading,
        val randomActivity: RandomActivity? = null,
        val showRandomActivityDialog: Boolean = false,
        val nearestHoliday: Holiday? = null,
        val showNearestHolidayDialog: Boolean = false,
    )

    override val _state = MutableStateFlow(State())


    sealed class Event {
        data object LogoutClicked : Event()
        data object BoredClicked : Event()
        data object DismissRandomActivityDialog : Event()
        data object HolidayClicked : Event()
        data object DismissHolidayDialog : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.LogoutClicked -> viewModelScope.launch {
                authManager.logout()
            }

            Event.BoredClicked -> _state.update {
                it.copy(showRandomActivityDialog = true)
            }
            Event.DismissRandomActivityDialog -> _state.update {
                it.copy(showRandomActivityDialog = false)
            }

            Event.HolidayClicked -> _state.update {
                it.copy(showNearestHolidayDialog = true)
            }
            Event.DismissHolidayDialog -> _state.update {
                it.copy(showNearestHolidayDialog = false)
            }
        }
    }

    init {
        val status = authManager.authStatus.value
        _state.update {
            it.copy(authStatus = status)
        }
        viewModelScope.launch {
            authManager.authStatus.collect {
                _state.update {  oldState ->
                    oldState.copy(authStatus = it)
                }
            }
        }
        loadRandomActivity()
        loadHolidays()
    }

    private fun loadRandomActivity() {
        viewModelScope.launch {
            val randomActivityResource = randomActivityRepo.getRandomActivity()
            when (randomActivityResource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(randomActivity = randomActivityResource.data)
                    }
                }
                else -> Unit
            }
        }
    }

    private fun loadHolidays() {
        viewModelScope.launch {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val holidayResource = nagerRepo.getHolidays(year = "$year", countryCode = COUNTRY_CODE_FOR_NAGER)
            when (holidayResource) {
                is Resource.Success -> {
                    processHolidays(holidayResource.data)
                }
                else -> Unit
            }
        }
    }

    private fun processHolidays(holidays: List<Holiday>) {
        val sorted = holidays.sortedBy { it.date }
        val nearest = sorted.firstOrNull {
            it.date > System.currentTimeMillis()
        }
        _state.update {
            it.copy(nearestHoliday = nearest)
        }
    }

    companion object {
        private const val COUNTRY_CODE_FOR_NAGER = "us"
    }

}