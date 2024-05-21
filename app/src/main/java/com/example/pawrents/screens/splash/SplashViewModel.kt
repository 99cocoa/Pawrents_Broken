/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.pawrents.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.common.LOGIN_SCREEN
import com.example.pawrents.common.PROFILE_SCREEN
import com.example.pawrents.common.SETTINGS_SCREEN
import com.example.pawrents.common.SIGN_UP_SCREEN
import com.example.pawrents.services.account.AccountService
import com.example.pawrents.services.login.LogService
import com.example.pawrents.common.SPLASH_SCREEN
import com.example.pawrents.common.TASKS_SCREEN
import com.example.pawrents.services.configuration.ConfigurationService
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  configurationService: ConfigurationService,
  private val accountService: AccountService,
  logService: LogService
) : PawrentsViewModel(logService) {
  val showError = mutableStateOf(false)

  init {
    launchCatching { configurationService.fetchConfiguration() }
  }

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {

    showError.value = false
    if (accountService.hasUser) openAndPopUp(PROFILE_SCREEN, SPLASH_SCREEN)
    else openAndPopUp(SIGN_UP_SCREEN, SPLASH_SCREEN)

      //createAnonymousAccount(openAndPopUp)
  }

 // private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
    //launchCatching(snackbar = false) {
    //  try {
     //   accountService.createAnonymousAccount()
     // } catch (ex: FirebaseAuthException) {
     //   showError.value = true
     //   throw ex
    //  }
    //  openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
    //}
//  }
}
