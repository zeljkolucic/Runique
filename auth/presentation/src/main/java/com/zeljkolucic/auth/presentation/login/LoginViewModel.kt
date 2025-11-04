package com.zeljkolucic.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeljkolucic.auth.domain.AuthRepository
import com.zeljkolucic.auth.domain.UserDataValidator
import com.zeljkolucic.auth.presentation.register.RegisterEvent
import com.zeljkolucic.core.domain.util.DataError
import com.zeljkolucic.core.domain.util.Result
import com.zeljkolucic.core.presentation.ui.R
import com.zeljkolucic.core.presentation.ui.UiText
import com.zeljkolucic.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(email.toString().trim()) &&
                    password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnRegisterClick -> Unit
            LoginAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)
            when(result) {
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.Error(
                            UiText.StringResource(R.string.error_email_password_incorrect)
                        ))
                    } else {
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }
            }
        }
    }
}