package com.zeljkolucic.run.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeljkolucic.core.domain.run.RunRepository
import com.zeljkolucic.core.domain.run.SyncRunScheduler
import com.zeljkolucic.run.presentation.overview.mapper.toRunUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class RunOverviewViewModel(
    private val runRepository: RunRepository,
    private val syncRunScheduler: SyncRunScheduler
): ViewModel() {
    private val eventChannel = Channel<RunOverviewAction>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(RunOverviewState())
        private set

    init {
        viewModelScope.launch {
            syncRunScheduler.scheduleSync(
                type = SyncRunScheduler.SyncType.FetchRuns(30.minutes)
            )
        }

        runRepository.getRuns().onEach { runs ->
            val runsUi = runs.map { it.toRunUi() }
            state = state.copy(runs = runsUi)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            runRepository.syncPendingRuns()
            runRepository.fetchRuns()
        }
    }
    fun onAction(action: RunOverviewAction) {
        when(action) {
            is RunOverviewAction.DeleteRun -> {
                viewModelScope.launch {
                    runRepository.deleteRun(action.runUi.id)
                }
            }
            RunOverviewAction.OnAnalyticsClick -> {

            }
            RunOverviewAction.OnLogoutClick -> {

            }
            RunOverviewAction.OnStartClick -> {

            }
        }
    }
}