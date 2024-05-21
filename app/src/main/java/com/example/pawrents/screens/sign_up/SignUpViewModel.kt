package com.example.pawrents.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.R
import com.example.pawrents.common.LOGIN_SCREEN
import com.example.pawrents.common.SETTINGS_SCREEN
import com.example.pawrents.common.SIGN_UP_SCREEN
import com.example.pawrents.common.TASKS_SCREEN
import com.example.pawrents.services.account.AccountService
import com.example.pawrents.services.login.LogService
import com.example.pawrents.common.snackbar.SnackbarManager
import com.example.pawrents.common.textvalidity.isValidEmail
import com.example.pawrents.common.textvalidity.isValidPassword
import com.example.pawrents.common.textvalidity.passwordMatches
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) :PawrentsViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onLoginClick (openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if(!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(R.string.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            openAndPopUp(TASKS_SCREEN, SIGN_UP_SCREEN)
        }
    }
}