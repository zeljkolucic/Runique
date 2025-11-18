package com.zeljkolucic.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeljkolucic.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

class ActiveRunViewModel(
    private val runningTracker: RunningTracker
): ViewModel() {
    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(false)

    init {
        _hasLocationPermission
            .onEach { hasPermission ->
                if(hasPermission) {
                    runningTracker.startObservingLocation()
                } else {
                    runningTracker.stopObservingLocation()
                }
            }
            .launchIn(viewModelScope)

        runningTracker
            .currentLocation
            .onEach { location ->
                Timber.d("New location: $location")
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ActiveRunAction) {
        when(action) {
            ActiveRunAction.OnBackClick -> {

            }
            ActiveRunAction.OnFinishRunClick -> {

            }
            is ActiveRunAction.OnLocationPermissionInfoSubmit -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
            is ActiveRunAction.OnNotificationPermissionInfoSubmit -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationRationale
                )
            }
            ActiveRunAction.OnRationaleDialogDismiss -> {
                state = state.copy(
                    showNotificationRationale = false,
                    showLocationRationale = false
                )
            }
            ActiveRunAction.OnResumeRunClick -> {

            }
            ActiveRunAction.OnStartClick -> {

            }
            ActiveRunAction.OnToggleRunClick -> {

            }
        }
    }
}