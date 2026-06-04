package com.slemenceu.taptrack.core.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun MyDialog(
    header: String,
    description: String,
    icon: ImageVector,
    primaryButtonText: String,
    primaryButtonColor: Color = red500,
    secondaryButtonText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        BackgroundThemeCard() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = red500.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = red500.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(horizontal = 27.dp, vertical = 17.dp)
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    text = header,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = description,
                    color = lightGrey400,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.height(24.dp))

                MySecondaryButton(
                    text = primaryButtonText,
                    textColor = primaryButtonColor,
                    borderColor = primaryButtonColor.copy(0.2f),
                    containerColor = primaryButtonColor.copy(0.1f),
                    onClick = onConfirm
                )
                secondaryButtonText?.let {
                    Spacer(Modifier.height(12.dp))
                    MyPrimaryButton(
                        text = secondaryButtonText,
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun MyDialogPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        MyDialog(
            header = "Do you want to dsconnect?",
            description = "This will disconnect the app from the PC.",
            icon = ImageVector.vectorResource(R.drawable.cancel_phn_icon),
            onConfirm = {},
            onDismiss = {},
            primaryButtonText = "Yes, Cancel",
            secondaryButtonText = "No",
            modifier = Modifier
                .padding(it)
        )
    }
}