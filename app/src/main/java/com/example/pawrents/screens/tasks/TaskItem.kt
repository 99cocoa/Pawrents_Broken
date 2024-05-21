package com.example.pawrents.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawrents.R
import com.example.pawrents.classes.Task
import com.example.pawrents.common.composable.DropdownMenu
import com.example.pawrents.common.textvalidity.hasDueDate
import com.example.pawrents.common.textvalidity.hasDueTime

@Composable
fun TaskItem(
    task: Task,
    options: List<String>,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = {onCheckChange()},
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column (
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.subtitle2
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = getDueDateAndTime(task),
                        fontSize = 12.sp)
                }
            }

            if (task.flag) {
                Icon (
                    painter = painterResource(id = R.drawable.ic_flag),
                    contentDescription = "Flag",
                    tint = colorResource(id = R.color.purple_200),
                )
            }
            DropdownMenu(
                options,
                Modifier.wrapContentWidth(),
                onActionClick
            )
        }
    }
}

private fun getDueDateAndTime (task: Task): String {
    val stringBuilder = StringBuilder("")

    if (task.hasDueDate()) {
        stringBuilder.append(task.dueDate)
        stringBuilder.append(" ")
    }
    if (task.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(task.dueTime)
    }
    return stringBuilder.toString()
}


