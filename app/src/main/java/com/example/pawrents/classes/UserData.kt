package com.example.pawrents.classes

import com.google.firebase.firestore.DocumentId

data class UserData(
    @DocumentId val profileId: String = "",
    val photo: String = "",
    val name: String = "",
    val animal: String = "",
    val age: String = "",
    val note1: String = "",
    val note2: String = "",
    val note3: String = "",
    val userId: String = "",
    val completed: Boolean = false,
)
