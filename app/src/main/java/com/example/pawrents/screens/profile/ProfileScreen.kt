package com.example.pawrents.screens.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawrents.R
import com.example.pawrents.classes.Task
import com.example.pawrents.classes.UserData
import com.example.pawrents.common.composable.ActionToolBar
import com.example.pawrents.common.composable.BasicTextButton
import com.example.pawrents.common.composable.ProfileImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction3

@Composable
fun ProfileScreen(
    openScreen: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userData = viewModel.userData.collectAsStateWithLifecycle(UserData())

    ProfileScreenContent(
        modifier = Modifier.fillMaxWidth(),
        userData = userData.value,
        onEditClick = viewModel::onEditClick,
        onGalleryClick = viewModel::onGalleryClick,
        onTasksClick = viewModel::onTasksClick,
        onSettingsClick = viewModel::onSettingsClick,
        openScreen = openScreen
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreenContent (
    modifier: Modifier = Modifier,
    userData: UserData,
    onEditClick: ((String) -> Unit, UserData, String) -> Unit,
    onGalleryClick:()->Unit,
    onTasksClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    openScreen: (String) -> Unit
) {
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolBar(
                title = R.string.tasks,
                endActionIcon = R.drawable.ic_settings,
                endAction = { onSettingsClick(openScreen) },
                modifier = Modifier.wrapContentSize(Alignment.TopEnd)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val picUrl: String by remember { mutableStateOf("") }
                    ProfileImage(Uri.parse(picUrl)) { updateProfilePicture(it) }

                    Column() {
                        BasicTextButton(
                            text = R.string.edit_profile,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            onEditClick(openScreen, userData, "edit_profile")
                        }
                        BasicTextButton(
                            text = R.string.to_gallery,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            onGalleryClick()
                        }

                        BasicTextButton(
                            text = R.string.to_tasks,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            onTasksClick(openScreen)
                        }
                    }
                }
                OutlinedTextField(
                        value = userData.name,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.name)) },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = userData.animal,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.animal)) },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )


            }
        }
    }
}

private fun updateProfilePicture(uri: Uri) {
    val riversRef =
        FirebaseStorage.getInstance()
            .getReference("profile_pictures/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")
    val uploadTask = riversRef.putFile(uri)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                    .update("profile_picture", uri.toString())
            }
        }
}
