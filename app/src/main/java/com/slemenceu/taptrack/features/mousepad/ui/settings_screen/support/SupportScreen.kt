package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.support

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun SupportScreen(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Support") }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(75.dp))
            BackgroundThemeCard() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .background(
                                color = blue500.copy(0.3f,),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "mail icon",
                            tint = green500,
                            modifier = Modifier
                                .size(40.dp)
                        )


                    }
                    Text(
                        text = "Need Help ?",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Contact our support team \nand we'll get back to you.",
                        color = lightGrey400,
                        fontSize = 16.sp,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Support Email",
                color = Color.White,
                fontSize = 20.sp,
            )
            Spacer(Modifier.height(16.dp))
            Surface(
                color = lightGrey800,
                border = BorderStroke(1.dp, darkBlue800),
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "sachinslemenceu@gmail.com",
                    color = lightGrey300,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            Spacer(Modifier.weight(0.5f))
            Text(
                text = "Typical response time 24-48 hrs",
                color = lightGrey300,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun SupportScreenPreview() {
    SupportScreen(onBackClicked = {})
}
