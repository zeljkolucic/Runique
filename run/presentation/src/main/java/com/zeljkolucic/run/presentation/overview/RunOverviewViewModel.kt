package com.zeljkolucic.run.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RunOverviewViewModel: ViewModel() {
    private val eventChannel = Channel<RunOverviewAction>()
    val events = eventChannel.receiveAsFlow()
    fun onAction(action: RunOverviewAction) {

    }
}