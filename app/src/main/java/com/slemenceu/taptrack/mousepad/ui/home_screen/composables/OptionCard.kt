package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.violet20
import kotlinx.coroutines.launch


@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String,
    @DrawableRes icon: Int,
) {
    val scale = remember { Animatable(1f) }
    val scope  =rememberCoroutineScope()
    Card(
        modifier = modifier
            .padding(10.dp)
            .scale(scale.value)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    scope.launch {
                        scale.animateTo(0.9f, animationSpec = tween(90))
                        scale.animateTo(1f, animationSpec = tween(120))
                    }

                }
            )
        ,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "Pc icon",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .size(40.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OptionCardPreview() {
    OptionCard(
        backgroundColor = violet20,
        text = "How to connect\n" +
                "to PC ?",
        icon = R.drawable.pc_icon,
    )

}