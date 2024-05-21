package com.example.pawrents.screens.profile

import com.example.pawrents.classes.UserData
import kotlinx.coroutines.flow.Flow

interface ProfileStorageService {
    val userData: Flow<UserData>
    suspend fun getUserInfo(userInfoId: String): UserData?
    suspend fun saveUserInfo(userData: UserData): String
    suspend fun updateUserInfo(userData: UserData)
}