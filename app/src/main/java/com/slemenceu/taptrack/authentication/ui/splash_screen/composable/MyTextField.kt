package com.slemenceu.taptrack.authentication.ui.splash_screen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.composables.PreviewBox
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey350
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    title: String = "Email Address",
    trailingIcon: @Composable () -> Unit = {},
    containerColor: Color = lightGrey800,
    contentColor: Color = Color.White,
    leadingIcon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = lightGrey400,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            placeholder = { Text(placeholder) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = green500,
                unfocusedBorderColor = darkBlue800,
                unfocusedTextColor = contentColor,
                focusedTextColor = contentColor,
                disabledTextColor = contentColor,
                unfocusedContainerColor = containerColor,
                focusedContainerColor = containerColor,
            ),
            shape = RoundedCornerShape(10.dp),
            prefix = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Icon",
                    tint = lightGrey350,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            },
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth(),
            )
    }
}

@Preview(showBackground = true)
@Composable
fun MyTextFieldPreview(modifier: Modifier = Modifier) {
    PreviewBox {
        MyTextField(
            value = "Hello",
            onValueChange = {},
            placeholder = "Placeholder",
            leadingIcon = Icons.Default.Email
        )
    }
}