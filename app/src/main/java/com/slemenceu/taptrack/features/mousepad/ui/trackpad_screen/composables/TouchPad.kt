package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.TrackpadUiEvent
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800


@Composable
fun TouchPad(
    onEvent: (TrackpadUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BackgroundThemeCard(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .pointerInput(Unit) {
                        forEachGesture {
                            awaitPointerEventScope {
                                val down = awaitFirstDown()
                                val startTime = System.currentTimeMillis()
                                var lastPos = down.position
                                var moved = false

                                while (true) {
                                    val event = awaitPointerEvent()
                                    val change =
                                        event.changes.firstOrNull { it.id == down.id } ?: break

                                    if (!change.pressed) break

                                    val currentPos = change.position
                                    val dx = (currentPos.x - lastPos.x).toInt()
                                    val dy = (currentPos.y - lastPos.y).toInt()

                                    val distance = dx * dx + dy * dy
                                    if (distance > 16) { // 4px movement threshold
                                        moved = true
                                        lastPos = currentPos
                                        try {
                                            onEvent(TrackpadUiEvent.SendTrackpadMove(dx, dy))
                                            Log.d("TouchPad", "Mouse move: dx=$dx, dy=$dy")
                                        } catch (e: Exception) {
                                            Log.e("TouchPad", "Mouse move failed: ${e.message}")
                                        }
                                    }

                                }

                                val duration = System.currentTimeMillis() - startTime
                                if (!moved) {
                                    try {
                                        if (duration >= 500) {
                                            onEvent(TrackpadUiEvent.SendClick(rightClick = true))
                                        } else {
                                            onEvent(TrackpadUiEvent.SendClick(rightClick = false))
                                        }
                                    } catch (e: Exception) {
                                        Log.e("TouchPad", "Click send failed: ${e.message}")
                                    }
                                }
                            }
                        }
                    }
            )
            Text(
                "Touch & drag to move cursor \nTwo fingers to scroll",
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                color = lightGrey300,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, darkBlue800),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(
                        "1-finger move",
                        textAlign = TextAlign.Center,
                        fontSize = 9.sp,
                        color = lightGrey400,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, darkBlue800),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(
                        "2-finger scroll",
                        textAlign = TextAlign.Center,
                        fontSize = 9.sp,
                        color = lightGrey400,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun TouchPadPreview() {
    TouchPad(
        onEvent = {}
    )
}

