
package com.slemenceu.taptrack.mousepad.ui.mousepad_screen.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.MouseUiEvent



@Composable
fun TouchPad(
    onEvent: (MouseUiEvent) -> Unit,
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
                            val change = event.changes.firstOrNull { it.id == down.id } ?: break

                            if (!change.pressed) break

                            val currentPos = change.position
                            val dx = (currentPos.x - lastPos.x).toInt()
                            val dy = (currentPos.y - lastPos.y).toInt()

                            val distance = dx * dx + dy * dy
                            if (distance > 16) { // 4px movement threshold
                                moved = true
                                lastPos = currentPos
                                try {
                                    onEvent(MouseUiEvent.SendMouseMove(dx, dy))
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
                                    onEvent(MouseUiEvent.SendClick(rightClick = true))
                                } else {
                                    onEvent(MouseUiEvent.SendClick(rightClick = false))
                                }
                            } catch (e: Exception) {
                                Log.e("TouchPad", "Click send failed: ${e.message}")
                            }
                        }
                    }
                }
            }
    )
}


