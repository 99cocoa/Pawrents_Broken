package com.example.pawrents.screens.edit_task

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
import com.example.pawrents.common.composable.ActionToolBar
import com.example.pawrents.common.composable.BasicField
import com.example.pawrents.common.composable.CardSelector
import com.example.pawrents.common.composable.RegularCardEditor
import com.example.pawrents.ui.theme.PawrentsTheme
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
fun EditTaskScreen(
    popUpScreen: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
){
    val task by viewModel.task
    val activity = LocalContext.current as AppCompatActivity

    EditTaskScreenContent (
        task = task,
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUrlChange = viewModel::onUrlChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onFlagToggle = viewModel::onFlagToggle,
        activity = activity
    )
}

@Composable
fun EditTaskScreenContent(
    modifier: Modifier = Modifier,
    task: Task,
    onDoneClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onFlagToggle: (String) -> Unit,
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
            title = R.string.edit_task,
            modifier =Modifier.wrapContentSize(Alignment.TopEnd),
            endActionIcon = R.drawable.ic_check,
            endAction = { onDoneClick() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
        
        BasicField(text = R.string.title, value = task.title, onNewValue = onTitleChange, modifier = fieldModifier)
        BasicField(text = R.string.description, value = task.description, onNewValue = onDescriptionChange, modifier = fieldModifier)
        BasicField(text = R.string.url, value = task.url, onNewValue = onUrlChange, modifier = fieldModifier)
        
        Spacer(modifier = Modifier.height(12.dp))
        CardEditors(task, onDateChange, onTimeChange, activity)
        CardSelectors(task, onFlagToggle)

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun CardEditors(
    task: Task,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    activity: AppCompatActivity?
){
    RegularCardEditor(
        title = R.string.date,
        icon = R.drawable.ic_calendar,
        content = task.dueDate ,
        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
    ) {
        showDatePicker(activity, onDateChange)
    }
    RegularCardEditor(
        title = R.string.time,
        icon = R.drawable.ic_clock,
        content = task.dueTime,
        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
    ) {
        showTimePicker(activity, onTimeChange)
    }
}

@Composable
fun CardSelectors (
    task: Task,
    onFlagToggle: (String) -> Unit
) {
    val flagSelection = EditFlagOption.getByCheckedState(task.flag).name

    CardSelector(R.string.flag, EditFlagOption.getOptions(), flagSelection, Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)) {newValue ->
        onFlagToggle(newValue)
    }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            timeInMillis -> onDateChange(timeInMillis)
        }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit){
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let{
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
                timeInMillis -> onTimeChange(picker.hour, picker.minute)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    val task = Task(
        title = "Task title",
        description = "Task description",
        flag = true
    )
    PawrentsTheme {
        EditTaskScreenContent(
            task = task,
            onDoneClick = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onUrlChange = { },
            onDateChange = { },
            onTimeChange = { _, _ -> },
            onFlagToggle = { },
            activity = null
        )
    }
}