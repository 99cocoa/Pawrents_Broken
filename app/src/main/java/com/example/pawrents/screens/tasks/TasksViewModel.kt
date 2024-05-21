package com.example.pawrents.screens.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.services.login.LogService
import com.example.pawrents.classes.Task
import com.example.pawrents.common.EDIT_TASK_SCREEN
import com.example.pawrents.common.SETTINGS_SCREEN
import com.example.pawrents.common.TASK_ID
import com.example.pawrents.services.configuration.ConfigurationService
import com.example.pawrents.services.storage.StorageService
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : PawrentsViewModel (logService){
    val options = mutableStateOf<List<String>>(listOf())
    val tasks = storageService.tasks

    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = TaskAction.getOptions(hasEditOption)
    }

    fun onTaskCheckChange  (task: Task) {
        launchCatching {
            storageService.update (task.copy(completed = !task.completed))
        }
    }

fun onAddClick (openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

fun onSettingsClick (openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
    when (TaskAction.getByTitle(action)) {
        TaskAction.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID = {${task.id}}")
        TaskAction.DeleteTask -> launchCatching {storageService.delete(task.id)}
        TaskAction.ToggleFlag -> launchCatching {storageService.update(task.copy(flag = !task.flag))}
        }
    }
}
