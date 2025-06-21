package com.slemenceu.taptrack.authentication.ui.splash_screen.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.ui.theme.darkGrey
import com.slemenceu.taptrack.ui.theme.darkViolet
import com.slemenceu.taptrack.ui.theme.violet10
import com.slemenceu.taptrack.ui.theme.violet20

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    containerColor: Color = Color.White,
    contentColor: Color = violet20,
    trailingIcon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = darkViolet,
            unfocusedBorderColor = Color.Transparent,
            unfocusedTextColor = contentColor,
            focusedTextColor = contentColor,
            disabledTextColor = contentColor,
            unfocusedContainerColor = containerColor,
            focusedContainerColor = containerColor,
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {Icon(
            imageVector = trailingIcon,
            contentDescription = "Email Icon",
            tint = darkViolet,
        )},
    )
}

@Preview(showBackground = true)
@Composable
fun MyTextFieldPreview(modifier: Modifier = Modifier) {
    MyTextField(
        value = "Hello",
        onValueChange = {},
        placeholder = "Placeholder",
        trailingIcon = Icons.Default.Email
    )
}