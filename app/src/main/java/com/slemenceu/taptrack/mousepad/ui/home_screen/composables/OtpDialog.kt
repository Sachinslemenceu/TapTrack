package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OtpDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var otp by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Enter OTP")
            },
            text = {
                OutlinedTextField(
                    value = otp,
                    onValueChange = {
                        if (it.length <= 4) otp = it  // limit to 4 digits
                    },
                    label = { Text("OTP") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(otp)
                    onDismiss()
                }) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
private fun OtpDialogPreview() {
    OtpDialog(showDialog = true, onDismiss = {}, onConfirm = {})
}
