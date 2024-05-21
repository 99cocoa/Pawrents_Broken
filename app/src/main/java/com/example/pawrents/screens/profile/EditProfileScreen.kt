package com.example.pawrents.screens.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pawrents.R
import com.example.pawrents.classes.Task
import com.example.pawrents.classes.UserData
import com.example.pawrents.common.composable.ActionToolBar
import com.example.pawrents.common.composable.BasicField
import com.example.pawrents.common.composable.CardSelector
import com.example.pawrents.common.composable.RegularCardEditor
import com.example.pawrents.screens.edit_task.EditFlagOption
import com.example.pawrents.ui.theme.PawrentsTheme
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
fun EditProfileScreen(
    popUpScreen: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
){
    val userData by viewModel.userData
    val activity = LocalContext.current as AppCompatActivity

    EditProfileScreenContent (
        userData = userData,
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onNameChange = viewModel::onNameChange,
        onAnimalChange = viewModel::onAnimalChange,
        onAgeChange = viewModel::onAgeChange,
        onNote1Change = viewModel::onNote1Change,
        onNote2Change = viewModel::onNote2Change,
        onNote3Change = viewModel::onNote3Change,
        activity = activity
    )
}

@Composable
fun EditProfileScreenContent(
    modifier: Modifier = Modifier,
    userData: UserData,
    onDoneClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onAnimalChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onNote1Change: (String) -> Unit,
    onNote2Change: (String) -> Unit,
    onNote3Change: (String) -> Unit,
    activity: AppCompatActivity?
){
    Column (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolBar(
            title = R.string.profile,
            modifier = Modifier.wrapContentSize(Alignment.TopEnd),
            endActionIcon = R.drawable.ic_check,
            endAction = { onDoneClick() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)

        BasicField(text = R.string.name, value = userData.name, onNewValue = onNameChange, modifier = fieldModifier)
        BasicField(text = R.string.animal, value = userData.animal, onNewValue = onAnimalChange, modifier = fieldModifier)
        BasicField(text = R.string.age, value = userData.age, onNewValue = onAgeChange, modifier = fieldModifier)
        Spacer(modifier = Modifier.height(12.dp))
        BasicField(text = R.string.note1, value = userData.note1, onNewValue = onNote1Change, modifier = fieldModifier)
        BasicField(text = R.string.note2, value = userData.note2, onNewValue = onNote2Change, modifier = fieldModifier)
        BasicField(text = R.string.note3, value = userData.note3, onNewValue = onNote3Change, modifier = fieldModifier)

        Spacer(modifier = Modifier.height(12.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    val userData = UserData(
        name = "Task title",
        animal = "Task description",
        age = "Task age",
        note1 = "Task note 1",
        note2 = "Task note 2",
        note3 = "Task note 3"
    )
    PawrentsTheme {
        EditProfileScreenContent(
            userData = userData,
            onDoneClick = { },
            onNameChange = { },
            onAnimalChange = { },
            onAgeChange = { },
            onNote1Change = { },
            onNote2Change = { },
            onNote3Change = { },
            activity = null
        )
    }
}