package com.example.pawrents.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.R
import com.example.pawrents.common.LOGIN_SCREEN
import com.example.pawrents.common.SETTINGS_SCREEN
import com.example.pawrents.common.TASKS_SCREEN
import com.example.pawrents.services.account.AccountService
import com.example.pawrents.services.login.LogService
import com.example.pawrents.common.snackbar.SnackbarManager
import com.example.pawrents.common.textvalidity.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val accountService : AccountService,
    logService: LogService
    ) : PawrentsViewModel(logService) {
        var uiState = mutableStateOf(LoginUiState())
            private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange (newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange (newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(
        openAndPopUp: (String, String) -> Unit
    ) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(TASKS_SCREEN, LOGIN_SCREEN)
        }
    }

        fun onForgotClick() {
            if (!email.isValidEmail()) {
                SnackbarManager.showMessage(R.string.email_error)
                return
            }

            launchCatching {
                accountService.sendRecoveryEmail(email)
                SnackbarManager.showMessage(R.string.recovery_email_sent)
            }
        }
    }

