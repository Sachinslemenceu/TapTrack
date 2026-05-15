package com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import kotlinx.coroutines.delay

enum class ConnectionStep {
    QR_SCANNED,
    ESTABLISHING_CONNECTION,
    VERIFYING_LATENCY,
    COMPLETED
}

@Composable
fun ConnectionStepProgressCard(
    currentStep: ConnectionStep,
    modifier: Modifier = Modifier
) {
    BackgroundThemeCard() {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            ConnectionStep.entries.forEach { step ->
                // Skip displaying the COMPLETED step itself
                if (step == ConnectionStep.COMPLETED) return@forEach

                val isStepCompleted = when {
                    currentStep == ConnectionStep.COMPLETED -> true // All steps are completed when done
                    step.ordinal < currentStep.ordinal -> true // Steps before current are completed
                    else -> false
                }
                val isStepCurrent = when {
                    currentStep == ConnectionStep.COMPLETED -> false // No current step when completed
                    else -> step == currentStep
                }

                StepItem(
                    step = step,
                    isCurrent = isStepCurrent,
                    isCompleted = isStepCompleted,
                    isLastStep = step == ConnectionStep.VERIFYING_LATENCY
                )
            }
        }
    }
}

@Composable
private fun StepItem(
    step: ConnectionStep,
    isCurrent: Boolean,
    isCompleted: Boolean,
    isLastStep: Boolean
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Left side with bullet point and vertical line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            // Bullet point container
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(20.dp)
            ) {
                when {
                    isCompleted -> {
                        // Green circle with tick for completed steps
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(green500.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Completed",
                                modifier = Modifier.size(12.dp),
                                tint = green500
                            )
                        }
                    }

                    isCurrent -> {
                        // Animated blinking blue dot with outer ring for current step
                        val infiniteTransition = rememberInfiniteTransition(label = "blink")
                        val alpha = infiniteTransition.animateFloat(
                            initialValue = 0.3f,
                            targetValue = 1.0f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(800, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "blink_alpha"
                        )

                        // Outer ring with semi-transparent fill
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(blue500.copy(alpha = 0.15f), CircleShape)
                                .border(1.dp, blue500.copy(0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            // Inner blinking dot
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(blue500.copy(alpha = alpha.value), CircleShape)
                            )
                        }
                    }

                    else -> {
                        // Gray dot for future steps
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(20.dp)
                                .background(darkBlue800, CircleShape)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(lightGrey300, CircleShape)
                            )
                        }
                    }
                }
            }

            // Vertical line (only if not the last step)
            if (!isLastStep) {
                Canvas(
                    modifier = Modifier
                        .width(2.dp)
                        .height(32.dp)
                ) {
                    drawLine(
                        color = darkBlue800,
                        strokeWidth = 2f,
                        start = Offset(x = size.width / 2, y = 0f),
                        end = Offset(
                            x = size.width / 2,
                            y = size.height
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Step text
        Text(
            text = when (step) {
                ConnectionStep.QR_SCANNED -> "QR Code Scanned"
                ConnectionStep.ESTABLISHING_CONNECTION -> "Establishing Connection"
                ConnectionStep.VERIFYING_LATENCY -> "Verifying Latency"
                ConnectionStep.COMPLETED -> "Completed"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                isCurrent -> blue500
                isCompleted -> green500
                else -> lightGrey300
            },
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
//            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConnectionStepProgressCardPreview() {
    var progress: ConnectionStep by remember { mutableStateOf(ConnectionStep.QR_SCANNED) }
    LaunchedEffect(Unit) {
        while (progress != ConnectionStep.COMPLETED) {
            delay(2000)
            progress = when (progress) {
                ConnectionStep.QR_SCANNED -> ConnectionStep.ESTABLISHING_CONNECTION
                ConnectionStep.ESTABLISHING_CONNECTION -> ConnectionStep.VERIFYING_LATENCY
                ConnectionStep.VERIFYING_LATENCY -> ConnectionStep.COMPLETED
                ConnectionStep.COMPLETED -> ConnectionStep.QR_SCANNED
            }
        }
    }
    BackgroundThemeCard() {
        ConnectionStepProgressCard(currentStep = progress)
    }

}