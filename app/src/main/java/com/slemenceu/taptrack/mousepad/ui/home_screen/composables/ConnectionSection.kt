package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.lightGrey
import kotlinx.coroutines.launch


@Composable
fun ConnectionSection(
    modifier: Modifier = Modifier,
    isConnected: Boolean ,
    screenWidth: Dp,
    onNavigateToMousepad: () -> Unit,
    onConnectToMousepad: () -> Unit
) {

    val text = if (isConnected) "Click to open the mousepad" else "Hold to sync with pc"
    val icon = if (isConnected) R.drawable.connected_mouse_img else R.drawable.cn_img
    val connectionEstIcon = if (isConnected) R.drawable.connection_est_img else R.drawable.connection_nest_img
    val scale = remember { Animatable(1f) }
    val scope  =rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBFBFB),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    modifier = Modifier
                        .scale(scale.value)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                scope.launch{
                                    scale.animateTo(
                                        targetValue = 0.9f,
                                        animationSpec = tween(
                                            durationMillis = 90,
                                            easing = FastOutLinearInEasing
                                        )
                                    )
                                    scale.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(
                                            durationMillis = 90,
                                            easing = FastOutLinearInEasing
                                        )
                                    )
                                    if(!isConnected){
                                        onConnectToMousepad()
                                    } else{
                                        onNavigateToMousepad()
                                    }
                                }

                            }
                        )
                ) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = "Connection Icon",
                        modifier = Modifier
                            .size(screenWidth * 0.4f)
                            .padding(30.dp)

                    )
                }
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier
                        .padding(top = 10.dp),
                    color = lightGrey,
                )
            }
            Image(
                painter = painterResource(connectionEstIcon),
                contentDescription = "connection showing Icon",
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                contentScale = ContentScale.Fit
            )


        }
    }
}

@Preview
@Composable
private fun ConnectionSectionPreview() {
    ConnectionSection(
        isConnected = true,
        screenWidth = LocalConfiguration.current.screenWidthDp.dp,
        onNavigateToMousepad = {},
        onConnectToMousepad = {}
    )
}