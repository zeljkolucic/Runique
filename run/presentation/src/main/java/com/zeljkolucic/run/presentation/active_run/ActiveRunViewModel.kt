package com.zeljkolucic.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel: ViewModel() {
    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(false)

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