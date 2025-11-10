package com.zeljkolucic.run.presentation.active_run

import com.zeljkolucic.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}