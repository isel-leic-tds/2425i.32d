package isel.leic.tds.ttt.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.leic.tds.ttt.model.Name

@Composable
fun ClashNameEdit(
    action: Action,
    onCancel: () -> Unit,
    onAction: (Name) -> Unit
) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        title = {
            Text("Clash to ${action.text}", style = MaterialTheme.typography.h5)
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                enabled = Name.isValid(name),
                onClick = { onAction(Name(name)) },
            ) { Text(action.text) }
        },
        dismissButton = {
            TextButton(onClick = onCancel) { Text("Cancel") }
        },
        text = {
            OutlinedTextField(name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
        }
    )
}