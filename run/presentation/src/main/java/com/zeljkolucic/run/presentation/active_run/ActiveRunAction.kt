package com.zeljkolucic.run.presentation.active_run

sealed interface ActiveRunAction {
    data object OnStartClick: ActiveRunAction
    data object OnToggleRunClick: ActiveRunAction
    data object OnFinishRunClick: ActiveRunAction
    data object OnResumeRunClick: ActiveRunAction
    data object OnBackClick: ActiveRunAction
    data class OnLocationPermissionInfoSubmit(
        val acceptedLocationPermission: Boolean,
        val showLocationRationale: Boolean
    ): ActiveRunAction
    data class OnNotificationPermissionInfoSubmit(
        val acceptedNotificationPermission: Boolean,
        val showNotificationRationale: Boolean
    ): ActiveRunAction
    data object OnRationaleDialogDismiss: ActiveRunAction
    class OnRunProcessed(val mapPictureBytes: ByteArray): ActiveRunAction
}