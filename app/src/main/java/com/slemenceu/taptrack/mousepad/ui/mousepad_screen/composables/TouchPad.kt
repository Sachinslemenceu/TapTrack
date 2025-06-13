

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
import kotlinx.coroutines.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer



@Composable
fun TouchPad(
    ip: String,
    port: Int
) {
    val coroutineScope = rememberCoroutineScope()
    var socket by remember { mutableStateOf<DatagramSocket?>(null) }

    DisposableEffect(Unit) {
        socket = DatagramSocket()
        onDispose {
            socket?.close()
        }
    }

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

                                coroutineScope.launch {
                                    try {
                                        socket?.let {
                                            sendMouseMove(ip, port, dx, dy, it)
                                        }
                                    } catch (e: Exception) {
                                        Log.e("TouchPad", "Mouse move failed: ${e.message}")
                                    }
                                }
                            }
                        }

                        val duration = System.currentTimeMillis() - startTime
                        if (!moved) {
                            coroutineScope.launch {
                                try {
                                    socket?.let {
                                        if (duration >= 500) {
                                            sendClick(ip, port, rightClick = true, it)
                                        } else {
                                            sendClick(ip, port, rightClick = false, it)
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e("TouchPad", "Click send failed: ${e.message}")
                                }
                            }
                        }
                    }
                }
            }
    )
}


suspend fun sendMouseMove(ip: String, port: Int, dx: Int, dy: Int, socket: DatagramSocket) {
    val buffer = ByteBuffer.allocate(9)
    buffer.put(0) // Command type: 0 = move
    buffer.putInt(dx)
    buffer.putInt(dy)

    val packet = DatagramPacket(buffer.array(), buffer.array().size, InetAddress.getByName(ip), port)
    withContext(Dispatchers.IO) {
        socket.send(packet)
    }
}

suspend fun sendClick(ip: String, port: Int, rightClick: Boolean, socket: DatagramSocket) {
    val buffer = ByteBuffer.allocate(1)
    buffer.put(if (rightClick) 2 else 1) // 1 = left click, 2 = right click

    val packet = DatagramPacket(buffer.array(), buffer.array().size, InetAddress.getByName(ip), port)
    withContext(Dispatchers.IO) {
        socket.send(packet)
    }
}
