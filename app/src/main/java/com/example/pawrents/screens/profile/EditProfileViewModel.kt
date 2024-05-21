package com.example.pawrents.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.classes.UserData
import com.example.pawrents.common.USER_INFO_ID
import com.example.pawrents.common.textvalidity.idFromParameter
import com.example.pawrents.services.login.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val profileStorageService: ProfileStorageService
) : PawrentsViewModel(logService){
    val userData = mutableStateOf(UserData())

    init {
        val userInfoId = savedStateHandle.get<String>(USER_INFO_ID)
        if (userInfoId != null) {
            launchCatching {
                userData.value = profileStorageService.getUserInfo(userInfoId.idFromParameter())?: UserData()
            }
        }
    }

    fun onNameChange (newValue: String){
        userData.value = userData.value.copy(name = newValue)
    }

    fun onAnimalChange (newValue: String){
        userData.value = userData.value.copy(animal = newValue)
    }

    fun onAgeChange (newValue: String){
        userData.value = userData.value.copy(age = newValue)
    }

    fun onNote1Change (newValue: String){
        userData.value = userData.value.copy(note1 = newValue)
    }

    fun onNote2Change (newValue: String){
        userData.value = userData.value.copy(note2 = newValue)
    }

    fun onNote3Change (newValue: String){
        userData.value = userData.value.copy(note3 = newValue)
    }


    fun onDoneClick(popUpScreen: () ->Unit) {
        launchCatching {
            val editedProfile = userData.value
            if(editedProfile.profileId.isBlank()){
                profileStorageService.saveUserInfo(editedProfile)
            } else {
                profileStorageService.updateUserInfo(editedProfile)
            }
            popUpScreen()
        }
    }
}