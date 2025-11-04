package com.zeljkolucic.auth.presentation.register

import com.zeljkolucic.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}