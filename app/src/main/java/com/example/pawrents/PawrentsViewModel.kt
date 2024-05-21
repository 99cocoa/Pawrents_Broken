package com.example.pawrents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawrents.services.login.LogService
import com.example.pawrents.common.snackbar.SnackbarManager
import com.example.pawrents.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class PawrentsViewModel (
    private val logService: LogService
) : ViewModel() {
        fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
            viewModelScope.launch(
                CoroutineExceptionHandler { _, throwable ->
                    if (snackbar) {
                        SnackbarManager.showMessage(throwable.toSnackbarMessage())
                    }
                    logService.logNonFatalCrash(throwable)
                },
                block = block
            )
}