package com.example.pawrents.screens.profile


import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.classes.UserData
import com.example.pawrents.common.EDIT_PROFILE_SCREEN
import com.example.pawrents.common.SETTINGS_SCREEN
import com.example.pawrents.common.TASKS_SCREEN
import com.example.pawrents.common.USER_INFO_ID
import com.example.pawrents.services.login.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService,
    private val profileStorageService: ProfileStorageService,
) : PawrentsViewModel(logService){

    val userData = profileStorageService.userData

    fun onProfileCheckChange  (userData: UserData) {
        launchCatching {
            profileStorageService.updateUserInfo (userData.copy(completed = !userData.completed))
        }
    }
    fun onGalleryClick (){

    }
     fun onTasksClick (openScreen: (String) -> Unit) = openScreen(TASKS_SCREEN)


    fun onEditClick (openScreen: (String) -> Unit, userData: UserData, action: String) = openScreen("$EDIT_PROFILE_SCREEN?$USER_INFO_ID = {${userData.profileId}}")

    fun onSettingsClick (openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)





}

