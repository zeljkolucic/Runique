package com.zeljkolucic.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zeljkolucic.auth.domain.UserDataValidator
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        snapshotFlow { state.email.text }
            .onEach { email ->
                state = state.copy(
                    isEmailValid = userDataValidator.isValidEmail(email.toString())
                )
            }
            .launchIn(viewModelScope)

        snapshotFlow { state.password.text }
            .onEach { password ->
                state = state.copy(
                    passwordValidationState = userDataValidator.validatePassword(password.toString())
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {

    }
}