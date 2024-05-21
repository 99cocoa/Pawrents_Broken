package com.example.pawrents.screens.profile


import com.example.pawrents.classes.UserData
import com.example.pawrents.services.account.AccountService
import com.example.pawrents.services.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileStorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : ProfileStorageService {
    override val userData: Flow<UserData> = callbackFlow {
        val listener = firestore
            .collection(USER_INFO)
            .whereEqualTo(USER_ID_FIELD, auth.currentUserId)

    }

    override suspend fun getUserInfo(userInfoId: String): UserData? =
        firestore.collection(USER_INFO).document(userInfoId).get().await().toObject()

    override suspend fun saveUserInfo(userData: UserData): String =
        trace(SAVE_USER_INFO) {
            val profileWithUserId = userData.copy( userId = auth.currentUserId)
            firestore.collection(USER_INFO).add(profileWithUserId).await().id
        }

    override suspend fun updateUserInfo(userData: UserData): Unit =
        trace(UPDATE_USER_INFO) {
            firestore.collection(USER_INFO).document(userData.profileId).set(userData).await()
        }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_INFO = "userInfo"
        private const val SAVE_USER_INFO = "saveUserInfo"
        private const val UPDATE_USER_INFO = "updateUserInfo"

    }
}