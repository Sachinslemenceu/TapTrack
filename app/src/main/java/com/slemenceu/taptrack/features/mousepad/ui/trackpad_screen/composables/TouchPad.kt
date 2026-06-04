package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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
import androidx.compose.ui.input.pointer.PointerInputChange
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
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            val down = awaitFirstDown()
                            val startTime = System.currentTimeMillis()
                            var lastPos = down.position
                            var lastScrollY = 0f
                            var moved = false
                            var isScrolling = false

                            while (true) {
                                val event = awaitPointerEvent()
                                val activeChanges = event.changes.filter { it.pressed }

                                if (activeChanges.isEmpty()) break

                                if (activeChanges.size >= 2) {
                                    // Two-finger scroll logic
                                    isScrolling = true
                                    moved = true // Prevent click on release
                                    
                                    // Average Y position of the first two active fingers
                                    val currentScrollY = (activeChanges[0].position.y + activeChanges[1].position.y) / 2
                                    
                                    if (lastScrollY != 0f) {
                                        val deltaY = (currentScrollY - lastScrollY).toInt()
                                        if (deltaY != 0) {
                                            // Send inverted delta for natural scroll if needed, 
                                            // here we send the raw delta
                                            onEvent(TrackpadUiEvent.SendScroll(-deltaY))
                                        }
                                    }
                                    lastScrollY = currentScrollY
                                    
                                    // Reset lastPos so mouse move doesn't "jump" when switching back to 1 finger
                                    lastPos = activeChanges[0].position
                                    
                                    event.changes.forEach { it.consume() }
                                } else {
                                    // Single finger move logic
                                    lastScrollY = 0f 
                                    val change = activeChanges[0]
                                    val currentPos = change.position
                                    
                                    // If we were just scrolling, don't move the mouse on the frame we switch back
                                    if (isScrolling) {
                                        lastPos = currentPos
                                        isScrolling = false
                                    } else {
                                        val dx = (currentPos.x - lastPos.x).toInt()
                                        val dy = (currentPos.y - lastPos.y).toInt()

                                        if (dx != 0 || dy != 0) {
                                            moved = true
                                            onEvent(TrackpadUiEvent.SendTrackpadMove(dx, dy))
                                            lastPos = currentPos
                                        }
                                    }
                                    change.consume()
                                }
                            }

                            // Handle click if no movement or scrolling occurred
                            val duration = System.currentTimeMillis() - startTime
                            if (!moved) {
                                val isRightClick = duration >= 500
                                onEvent(TrackpadUiEvent.SendClick(isRightClick))
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
                InstructionChip("1-finger move")
                Spacer(Modifier.width(12.dp))
                InstructionChip("2-finger scroll")
            }
        }
    }
}

@Composable
private fun InstructionChip(text: String) {
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, darkBlue800),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            fontSize = 9.sp,
            color = lightGrey400,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
        )
    }
}

@Preview
@Composable
private fun TouchPadPreview() {
    TouchPad(onEvent = {})
}
